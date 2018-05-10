package pt.zenit.ztack.cayenne.modeler.utility;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Classe utilities para objetos {@link org.apache.cayenne.map.ObjEntity} do cayenne
 */
public class ObjectEntityUtilities
{
    private ObjectEntityUtilities() {
        throw new AssertionError("Util Class!");
    }

    /**
     * Tipos os tipos de data configurados
     */
    public static final ImmutableList<String> REGISTERED_TYPE_NAMES = ImmutableList.of(
        String.class.getName(),             //String
        BigDecimal.class.getName(),         //BigDecimal
        BigInteger.class.getName(),         //BigInteger
        Boolean.class.getName(),            //Boolean
        Byte.class.getName(),               //Byte
        Character.class.getName(),          //Character
        Date.class.getName(),               //SQL Date
        java.util.Date.class.getName(),     //Date
        Double.class.getName(),             //Double
        Float.class.getName(),              //Float
        Integer.class.getName(),            //Integer
        Long.class.getName(),               //Long
        Short.class.getName(),              //Short
        Time.class.getName(),               //Time
        Timestamp.class.getName(),          //Timestamp
        Date.class.getName(),               //Date
        GregorianCalendar.class.getName(),  //GreforianCalendar
        Calendar.class.getName(),           //Calendar
        UUID.class.getName(),               //UUID
        Serializable.class.getName(),       //Serilizable
        "java.lang.Character[]",            //Character[]
        "java.lang.Byte[]",                 //Byte
        "java.time.LocalDate",              //LocalDate
        "java.time.LocalTime",              //LocalTime
        "java.time.LocalDateTime",          //LocalDateTime
         /* PRIMITIVOS */
        "boolean", "byte", "byte[]", "char", "char[]", "double", "float", "int", "long", "short"
    );



}
