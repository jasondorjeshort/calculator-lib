package io.github.endreman0.calculator.expression.type;

import java.util.Calendar;

import io.github.endreman0.calculator.annotation.Factory;
import io.github.endreman0.calculator.annotation.Function;
import io.github.endreman0.calculator.annotation.Operator;
import io.github.endreman0.calculator.util.Patterns;
import io.github.endreman0.calculator.util.Utility;

public class Time extends Type{
	private int hours, minutes, seconds;
	public static Time valueOf(int hours, int minutes){return new Time(hours, minutes, 0);}
	public static Time valueOf(int hours, int minutes, int seconds){return new Time(hours, minutes, seconds);}
	@Factory(Patterns.TIME)
	public static Time valueOf(String input){
		int ind1 = input.indexOf(':'), ind2 = input.indexOf(':', ind1+1);
		if(ind2 > -1)//Seconds included
			return valueOf(
					Integer.parseInt(input.substring(0, ind1)),//Hours
					Integer.parseInt(input.substring(ind1+1, ind2)),//Minutes
					Integer.parseInt(input.substring(ind2+1)));//Seconds
		else return valueOf(Integer.parseInt(input.substring(0, ind1)), Integer.parseInt(input.substring(ind1+1)));//Hours and minutes
	}
	private Time(int hours, int minutes, int seconds){
		while(seconds >= 60){minutes++; seconds -= 60;}
		while(seconds < 0){minutes--; seconds += 60;}
		
		while(minutes >= 60){hours++; minutes -= 60;}
		while(minutes < 0){hours--; minutes += 60;}
		
		while(hours >= 24) hours -= 24;
		while(hours < 0) hours += 24;
		this.hours = hours; this.minutes = minutes; this.seconds = seconds;
	}
	
	@Operator("+")
	public Time add(Time other){
		Utility.checkNull(other, "Cannot add null");
		return new Time(hours + other.hours, minutes + other.minutes, seconds + other.seconds);
	}
	
	@Operator("-")
	public Time subtract(Time other){
		Utility.checkNull(other, "Cannot subtract null");
		return new Time(hours - other.hours, minutes - other.minutes, seconds - other.seconds);
	}
	
	public int hours(){return hours;}
	public int minutes(){return minutes;}
	public int seconds(){return seconds;}
	public boolean isAM(){return hours < 12;}
	public boolean isPM(){return hours >= 12;}
	
	@Function("hours") public MixedNumber fnHours(){return MixedNumber.valueOf(hours);}
	@Function("minutes") public MixedNumber fnMinutes(){return MixedNumber.valueOf(minutes);}
	@Function("seconds") public MixedNumber fnSeconds(){return MixedNumber.valueOf(seconds);}
	@Function("isAM") public Switch fnIsAM(){return Switch.valueOf(isAM());}
	@Function("isPM") public Switch fnIsPM(){return Switch.valueOf(isPM());}
	
	@Function public MixedNumber partialHours(){return MixedNumber.valueOf(hours, minutes*60 + seconds, 3600);}
	@Function public MixedNumber partialMinutes(){return MixedNumber.valueOf(hours*60 + minutes, seconds, 60);}
	@Function public MixedNumber partialSeconds(){return MixedNumber.valueOf(hours*3600 + minutes*60 + seconds);}
	
	@Function
	public static Time now(){
		Calendar time = Calendar.getInstance();
		return valueOf(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
	}
	
	public Time clone(){return new Time(hours, minutes, seconds);}
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Time)) return false;
		Time other = (Time)obj;
		return hours == other.hours && minutes == other.minutes && seconds == other.seconds;
	}
	@Override public int hashCode(){return hours;}
	@Override
	public String toParseableString(){
		if(seconds == 0) return hours + ":" + minutes;
		else return hours + ":" + minutes + ":" + seconds;
	}
	@Override
	public String toDisplayString(){
		int hours;
		if(this.hours == 0) hours = 12;
		else if(this.hours <= 12) hours = this.hours;
		else hours = this.hours - 12;
		
		if(seconds == 0) return String.format("%d:%02d%s", hours, minutes, isAM() ? "AM" : "PM");
		else return String.format("%d:%02d:%02d%s", hours, minutes, seconds, isAM() ? "AM" : "PM");
	}
	@Override public String toDescriptorString(){return "Time[" + hours + "," + minutes + "," + seconds + "]";}
}
