/*
 * CalendarItems.java
 *
 * Created on 2007-9-16, 23:23:45
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dyno.swing.designer.properties.items;

import dyno.swing.designer.properties.types.Item;
import java.util.Calendar;

/**
 *
 * @author rehte
 */
public class CalendarItems implements ItemProvider {

    private static Item[] VALUE_ITEMS = {
        new Item("Era", Calendar.ERA, "java.util.Calendar.ERA"), 
        new Item("Year", Calendar.YEAR, "java.util.Calendar.YEAR"), 
        new Item("Month", Calendar.MONTH, "java.util.Calendar.MONTH"), 
        new Item("Week of Year", Calendar.WEEK_OF_YEAR, "java.util.Calendar.WEEK_OF_YEAR"), 
        new Item("Week of Month", Calendar.WEEK_OF_MONTH, "java.util.Calendar.WEEK_OF_MONTH"), 
        new Item("Day of Month", Calendar.DAY_OF_MONTH, "java.util.Calendar.DAY_OF_MONTH"), 
        new Item("Day of Year", Calendar.DAY_OF_YEAR, "java.util.Calendar.DAY_OF_YEAR"), 
        new Item("Day of Week", Calendar.DAY_OF_WEEK, "java.util.Calendar.DAY_OF_WEEK"), 
        new Item("Day of Week in Month", Calendar.DAY_OF_WEEK_IN_MONTH, "java.util.Calendar.DAY_OF_WEEK_IN_MONTH"), 
        new Item("AM/PM", Calendar.AM_PM, "java.util.Calendar.AM_PM"), 
        new Item("Hour", Calendar.HOUR, "java.util.Calendar.HOUR"),        
        new Item("Hour of Day", Calendar.HOUR_OF_DAY, "java.util.Calendar.HOUR_OF_DAY"), 
        new Item("Minute", Calendar.MINUTE, "java.util.Calendar.MINUTE"), 
        new Item("Second", Calendar.SECOND, "java.util.Calendar.SECOND"), 
        new Item("Millisecond", Calendar.MILLISECOND, "java.util.Calendar.MILLISECOND")
    };

    @Override
    public Item[] getItems() {
        return VALUE_ITEMS;
    }
}