package life.expert.algo.research.app;

//---------------------------------------------
//      ___       __        _______   ______
//     /   \     |  |      /  _____| /  __  \
//    /  ^  \    |  |     |  |  __  |  |  |  |
//   /  /_\  \   |  |     |  | |_ | |  |  |  |
//  /  _____  \  |  `----.|  |__| | |  `--'  |
// /__/     \__\ |_______| \______|  \______/
//
//               wilmer 2019/08/20
//---------------------------------------------

import life.expert.algo.research.domain.model.entity.Uno;
import life.expert.algo.research.domain.repository.UnoRepository;
import lombok.NonNull;//@NOTNULL

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.text.MessageFormat.format;           //format string

import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.*;   //checkArgument
//import static life.expert.common.base.Preconditions.*;  //checkCollection
import static org.apache.commons.lang3.Validate.*;      //notEmpty(collection)

import org.apache.commons.lang3.StringUtils;            //isNotBlank

import java.util.function.*;                            //producer supplier

import static java.util.stream.Collectors.*;            //toList streamAPI

import java.util.Optional;

import static reactor.core.publisher.Mono.*;
import static reactor.core.scheduler.Schedulers.*;
//import static  reactor.function.TupleUtils.*; //reactor's tuple->R INTO func->R
import static life.expert.common.function.TupleUtils.*; //vavr's tuple->R INTO func->R

import life.expert.value.string.*;
import life.expert.value.numeric.*;

import static life.expert.common.async.LogUtils.*;        //logAtInfo
import static life.expert.common.function.NullableUtils.*;//.map(nullableFunction)
import static life.expert.common.function.CheckedUtils.*;// .map(consumerToBoolean)
import static life.expert.common.reactivestreams.Preconditions.*; //reactive check
import static life.expert.common.reactivestreams.Patterns.*;    //reactive helper functions
import static life.expert.common.base.Objects.*;          //deepCopyOfObject
import static life.expert.common.reactivestreams.ForComprehension.*; //reactive for-comprehension

import static cyclops.control.Trampoline.more;
import static cyclops.control.Trampoline.done;

//import static io.vavr.API.*;                           //conflicts with my reactive For-comprehension

import static io.vavr.API.$;                            // pattern matching
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.*;                         //switch - case - success/failure
import static io.vavr.Predicates.*;                       //switch - case
//import static java.util.function.Predicate.*;           //isEqual streamAPI

import static io.vavr.API.CheckedFunction;//checked functions
import static io.vavr.API.unchecked;    //checked->unchecked
import static io.vavr.API.Function;     //lambda->Function3
import static io.vavr.API.Tuple;

import static io.vavr.API.Try;          //Try

import io.vavr.control.Try;                               //try

import static io.vavr.API.Failure;
import static io.vavr.API.Success;
import static io.vavr.API.Left;         //Either
import static io.vavr.API.Right;

//import java.util.List;                                  //usual list
//import io.vavr.collection.List;                         //immutable List
//import com.google.common.collect.*;                     //ImmutableList

import io.r2dbc.spi.ConnectionFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static life.expert.common.async.LogUtils.*;        //logAtInfo

@RunWith( SpringRunner.class )
//@SpringBootTest
@DataR2dbcTest
//@ComponentScan( { "life.expert.algo.research.app", "life.expert.algo.research.repository"  } )
@EnableR2dbcRepositories( basePackages = { "life.expert.algo.research.app" ,
                                           "life.expert.algo.research.domain.repository" } )
//@TestPropertySource( properties = { "spring.r2dbc.schema=classpath:schema.sql", "spring.r2dbc.initialization-mode=always"})

public class UnoRepositoryTest
	{
	@Autowired private UnoRepository unoRepository;
	
	@Autowired ConnectionFactory connectionFactory;
	
	@Data
	@AllArgsConstructor
	@Table
	public static class Reservation
		{
		@Id public Long id;
		
		public String name;
		}
	
	/**
	 * Context loads.
	 */
	@Test
	public void connectionFactoryTest()
		{
		//print( "tst_________________1__________________" );
		
		var client = DatabaseClient.create( connectionFactory );
		client.execute( "CREATE TABLE RESERVATION (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(255))" )
		      .fetch()
		      .all()
		      //.doOnEach( x -> System.out.println( "++++++++++ " + x ) )
		      .log()
		      .as( StepVerifier::create )
		      .verifyComplete();
		
		client.insert()
		      .into( Reservation.class )
		      .using( new Reservation( null , "NEW RESERVATION" ) )
		      .then()
		      .as( StepVerifier::create )
		      .verifyComplete();
		
		client.select()
		      .from( Reservation.class )
		      .fetch()
		      .all()
		      //.doOnEach( x -> System.out.println( "++++++++++ " + x ) )
		      .as( StepVerifier::create )
		      .expectNext( new Reservation( 1L , "NEW RESERVATION" ) )
		      .verifyComplete();
		}
	
	@Test
	public void repositoryTest()
		{
		this.unoRepository.findAll()
		                  .flatMap( r -> this.unoRepository.deleteById( r.getId() ) )
		                  .as( StepVerifier::create )
		                  .expectNextCount( 0 )
		                  .verifyComplete();
		
		Flux.just( "first" , "second" , "third" )
		    .map( name -> new Uno( null , name ) )
		    .flatMap( r -> this.unoRepository.save( r ) )
		    .as( StepVerifier::create )
		    .expectNextCount( 3 )
		    .verifyComplete();
		
		this.unoRepository.findAll()
		                  .as( StepVerifier::create )
		                  .expectNextCount( 3 )
		                  .verifyComplete();
		
		unoRepository.findByName( "first" )
		             .as( StepVerifier::create )
		             .expectNextCount( 1 )
		             .verifyComplete();
		}
		
	}


