package com.bms.dao;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.OfferException;
import com.bms.model.Offer;

@Repository
public class OfferDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public long countAll(){
		return  jdbcTemplate.queryForLong("select count(*) from offer_master");
	}
	

	public void save(Offer offer) throws OfferException {
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
	
		try{
			Date d1 = simpleDateFormat.parse(offer.getOfferDate());
			Date d2=simpleDateFormat.parse(offer.getExpiryDate());
			Object args[] = { offer.getType(), new java.sql.Date(d1.getTime()),
					new java.sql.Date(d2.getTime()), offer.getUtilized(),offer.getCustomerId()};
		
		jdbcTemplate
				.update("insert into offer_master (type,offer_date,expiry_date,utilized,customer_id) values (?,?,?,?,?)", args);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();	
			throw new OfferException("Requested customer does not exist, Please enter a valid CustomerId !!");
		} catch (ParseException e) {
			e.printStackTrace();
			throw new OfferException("Not a valid date format!!");
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new OfferException("Offer can't be saved!!");
		}
	}

	
	
	public void update(Offer offer) throws OfferException {

		Object args[] = {offer.getType(), offer.getOfferDate(),
				offer.getExpiryDate(), offer.getUtilized(),offer.getId(), };
		try{
		jdbcTemplate.update("update offer_master set type = ?,  offer_date= ?,expiry_date = ?,utilized = ?,customer_id = ? where offer_id = ?;",
				args);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new OfferException("Offer can't be updated!!");
		}
	}

	public List<Offer> readAll() throws OfferException {
		try{
		return jdbcTemplate.query("select * from offer_master",
				new OfferRowMapper());
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new OfferException("Offer can't be retrieved!!");
		}
	}

	public Offer readById(int id) throws OfferException {
		Object args[] = { id };
		try{
		return jdbcTemplate.queryForObject(
				"select * from offer_master where customer_id=?",
				new OfferRowMapper(), args);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new OfferException("Offer not found for the customer!!");
		}
	}

}
