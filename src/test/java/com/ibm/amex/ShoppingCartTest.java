package com.ibm.amex;
 
import org.junit.*;
import static org.junit.Assert.*;
 
public class ShoppingCartTest{
    
    private ShoppingCart cart;
    private Product book1;
    
   @Before
    public void setUp() {
        cart = new ShoppingCart();
        book1 = new Product("Maven in Action", 120.00);
        cart.addItem(book1);
    }
 
    @After	
    public void tearDown() {
	book1=null;
    	cart.empty();
	cart=null;  
    }
  
    @Test  
    public void testEmpty() {
	cart.empty();
        assertEquals(0, cart.getItemCount());
    }

    @Test	
    public void testBalance() {
	
        Product book2 = new Product("DOJO", 50.00);
        cart.addItem(book2);
        assertTrue(cart.getBalance()==170.00);
    }


    @Test	
    public void testAddItem() {
	
        Product book2 = new Product("DOJO", 50.50);
        cart.addItem(book2);
        assertEquals(2, cart.getItemCount());
    }
        
    @Test	
    public void testRemoveItem() throws ProductNotFoundException {
        cart.removeItem(book1);
        assertEquals(0, cart.getItemCount());
    }
    
    @Test(expected=ProductNotFoundException.class)
    public void testRemoveItemNotInCart() throws ProductNotFoundException{
            Product book3 = new Product("Ant inAction", 19.95);
            cart.removeItem(book3);
    }
}
