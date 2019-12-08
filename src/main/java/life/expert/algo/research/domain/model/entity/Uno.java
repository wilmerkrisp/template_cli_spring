package life.expert.algo.research.domain.model.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//<editor-fold desc=".">
/*

@Data означает
    @ToString,
    @EqualsAndHashCode,
    @Getter
    @Setter
    @RequiredArgsConstructor



возможные аннотации для R2DBC
    @Id: Applied at the field level to mark the primary used for identity purpose.
    @Table: Applied at the class level to indicate this class is a candidate for mapping to the database. You can specify the name of the table where the database will be stored.
    @Transient: By default all private fields are mapped to the row, this annotation excludes the field where it is applied from being stored in the database
    @PersistenceConstructor: Marks a given constructor - even a package protected one - to use when instantiating the object from the database. Constructor arguments are mapped by name to the key values in the retrieved row.
    @Column: Applied at the field level and described the name of the column as it will be represented in the row thus allowing the name to be different than the fieldname of the class.




  - класс сделан final тк класс изначально не предназначался для наследования
  
 
 i) если нужно закешировать свойство то пометить его аннотацией
  @Getter( lazy=true)

j) для игнорирования свойства, назвать его с $
   String $ name
   
*/
//</editor-fold>

/**
 * simple mutable class: Long String
 *
 * - pattern new-set-call
 * - not for inheritance
 *
 * <pre>{@code
 *    //pattern new-set-call
 *    Uno o = new Uno();
 * o.setName("a");
 * o.compute();
 *    }</pre>
 */

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Table
public final class Uno
	{
	
	private static final ResourceBundle bundle_        = ResourceBundle.getBundle( "messages" );
	
	private static final String         HELLO_MESSAGE_ = bundle_.getString( "hello" );
	
	/**
	 * id
	 *
	 * -- SETTER --
	 *
	 * @param id
	 * 	id
	 *
	 * 	-- GETTER --
	 * @return the id
	 */
	@Id private Long id;
	
	/**
	 * name
	 *
	 * -- SETTER --
	 *
	 * @param name
	 * 	name
	 *
	 * 	-- GETTER --
	 * @return the name
	 */
	private String name;
	
	//////////////////////////////////////////////////////////////////////////////////
	//  add your own methods
	//////////////////////////////////////////////////////////////////////////////////
	/*
	 *  1) no method args, because all inside props
	 *
	 *  2) количество параметров должно быть не более 4-х, иначе:
	 *    - разбить метод на несколько (как например в NEW-SET-CALL для каждого параметра отдельный сетер)
	 *    - вынести группу параметров в value-класс (nested static) (helper class)
	 *    - все параметры зашить в паттерн билдер, где параметры можно присваивать по-очереди (так что билдер применим и на мутабельном объекте)
	 *
	 
	 */
	
	/**
	 * Check consistency.
	 *
	 * @throws IllegalStateException
	 * 	if some object's property null or empty
	 * @implSpec Method depends on isConsistent()
	 */
	public final void checkConsistency()
		{
		if( !isConsistent() )
			throw new IllegalStateException();
		}
	
	/**
	 * Check consistency.
	 *
	 * @return false  if some object's property null or empty
	 */
	public final boolean isConsistent()
		{
		if( getId() == null )
			return false;
		if( getName() == null )
			return false;
		return true;
		}
	
	/**
	 * Compute optional.
	 *
	 *
	 * <pre>{@code
	 *           var o=compute("string");
	 *  }</pre>
	 *
	 * @param string
	 * 	the string
	 *
	 * @return empty value if input argument empty (or if one collection's element is empty)
	 *
	 * @throws IllegalStateException
	 * 	if some object's property null or empty
	 */
	public final Optional<String> compute( final String string )
		{
		if( string == null || string.isBlank() || !isConsistent() )
			{
			logAtWarning( "string param must not ne null or empty" );
			return Optional.empty();
			}
		
		log( "compute({})" , string );
		
		return Optional.ofNullable( string );
		}
		
	}
