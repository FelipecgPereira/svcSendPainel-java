package domain.userCases;

import com.google.gson.Gson;

import domain.models.Departure;

public class DepartureFormater{
  public Departure execute(String input){
    Gson gson = new Gson();
    Departure departure = gson.fromJson(input, Departure.class);
    return departure;
  }
}
