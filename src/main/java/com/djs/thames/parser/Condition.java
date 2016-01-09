package com.djs.thames.parser;


public class Condition {

	private int reach;
	private int state;
	private static int index = -1;

	private static String[] reaches = {
	"Upstream of St John's Lock",
	"St John's Lock to Buscot Lock",
	"Buscot Lock to Grafton Lock",
	"Grafton Lock to Radcot Lock",
	"Radcot Lock to Rushey Lock",
	"Rushey Lock to Shifford Lock",
	"Shifford Lock to Northmoor Lock",
	"Northmoor Lock to Pinkhill Lock",
	"Pinkhill Lock to Eynsham Lock",
	"Eynsham Lock to King's Lock",
	"King's Lock to Godstow Lock",
	"Godstow Lock to Osney Lock",
	"Osney Lock to Iffley Lock",
	"Iffley Lock to Sandford Lock",
	"Sandford Lock to Abingdon Lock",
	"Abingdon Lock to Culham Lock",
	"Culham Lock to Clifton Lock",
	"Clifton Lock to Day's Lock",
	"Day's Lock to Benson Lock",
	"Benson Lock to Cleeve Lock",
	"Cleeve Lock to Goring Lock",
	"Goring Lock to Whitchurch Lock",
	"Whitchurch Lock to Mapledurham Lock",
	"Mapledurham Lock to Caversham Lock",
	"Upstream of Blake's Lock",
	"Caversham Lock to Sonning Lock",
	"Sonning Lock to Shiplake Lock",
	"Shiplake Lock to Marsh Lock",
	"Marsh Lock to Hambleden Lock",
	"Hambleden Lock to Hurley Lock",
	"Hurley Lock to Temple Lock",
	"Temple Lock to Marlow Lock",
	"Marlow Lock to Cookham Lock",
	"Cookham Lock to Boulters Lock",
	"Boulters Lock to Bray Lock",
	"Bray Lock to Boveney Lock",
	"Boveney Lock to Romney Lock",
	"Romney Lock to Old Windsor Lock",
	"Old Windsor Lock to Bell Weir Lock",
	"Bell Weir Lock to Penton Hook Lock",
	"Penton Hook Lock to Chertsey Lock",
	"Chertsey Lock to Shepperton Lock",
	"Shepperton Lock to Sunbury Lock",
	"Sunbury Lock to Molesey Lock",
	"Molesey Lock to Teddington Lock" };

	private static String[] states = {
		"No stream warnings",			// 0
		"Caution stream increasing",	// 1
		"Caution stream decreasing",	// 2
		"Caution strong stream"			// 3
	};

	public static Condition createCondition( String reachName, String stateName ){

		Condition condition = null;

		int reach = resolveReach(reachName);
		int state = resolveState(stateName);

		if( reach >=0 && state >= 0){
			condition = new Condition();
			condition.setReach(reach);
			condition.setState(state);
		}

		return condition;
	}

	private static int resolveReach(String reachName){
		if( reachName != null && reachName.length() > 0){

			index ++;
			if( index >=0 &&
				index < reaches.length &&
				reaches[index].equals(reachName)){

				return index;
			}
			else{
				for( int i=0; i<reaches.length; i++) {

					if( reaches[index].equals(reachName)){
						index = i;
						return i;
					}
				}
			}

			// Not found
			// TODO - Log it
		}
		else {
			// TODO - LOG it - empty reach name passed in
		}

		return -1;
	}

	public static int resolveState(String stateName){

		if( stateName != null && stateName.length() > 0 ){
			if( stateName.contains("increasing")){
				return 1;
			}
			if( stateName.contains("decreasing")){
				return 2;
			}
			if( stateName.contains("strong")){
				return 3;
			}
			else {
				return 0;
			}
		}

		return -1;
	}


	public static String[] getReaches() {
		return reaches;
	}

	public static String[] getStates() {
		return states;
	}

	public int getReach() {
		return reach;
	}

	public void setReach(int reach) {
		this.reach = reach;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getReachName(){
		if( reach >=0 && reach < reaches.length){
			return reaches[reach];
		}

		return "";
	}

	public String getStateName(){
		if( state >=0 && state < states.length){
			return states[state];
		}

		return "";
	}
}
