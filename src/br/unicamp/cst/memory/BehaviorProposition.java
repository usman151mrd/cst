/*******************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     K. Raizer, A. L. O. Paraense, R. R. Gudwin - initial API and implementation
 ******************************************************************************/

package br.unicamp.cst.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an object used to store a behavior proposition, composed by the behavior's name, its add list and delete list.
 * It is useful to build strings with this information for memory objects.
 * @author klaus
 *
 */

public class BehaviorProposition {

private String name;
private ArrayList<String> addList=new ArrayList<String>();
private ArrayList<String> delList=new ArrayList<String>();
private ArrayList<String> actionList=new ArrayList<String>();

private boolean performed; //defines if the memory object holding this  information has been used or not
private String behaviorPropositionString;

	public BehaviorProposition(String name, ArrayList<String> addList, ArrayList<String> delList,ArrayList<String> actionList){
		this.name=name;
		this.addList.addAll(addList);
		this.delList.addAll(delList);
		this.performed=false;
		this.actionList=actionList;
		createBehaviorPropositionString();
		
	}
	public BehaviorProposition(String behaviorPropositionString){
		this.behaviorPropositionString=behaviorPropositionString;
		createBehaviorPropositions();
		
	}

	public ArrayList<String> getActionList() {
		return actionList;
	}
	public void setActionList(ArrayList<String> actionList) {
		this.actionList = actionList;
	}
	private void createBehaviorPropositions() {
		String[] tempString;
		
		tempString=behaviorPropositionString.split("<NAME>");
		if((tempString!=null)&&(tempString.length>1)){ //unnecessary check
			this.name=tempString[1];
		}else{this.name=null;}
		
		tempString=behaviorPropositionString.split("<PERFORMED>"); //unnecessary check
		if((tempString!=null)&&(tempString.length>1)){
			if(tempString[1].equals("true")){performed=true;} else {performed=false;} //TODO could there be a problem here in case recorded data were corrupted?
		}else{performed=false;}//without other information, i assume it was not used
		
		tempString=behaviorPropositionString.split("<DELLIST>");
		if((tempString!=null)&&(tempString.length>1)){
			tempString=tempString[1].substring(0, tempString[1].length()-1).split(",");//Exception in thread "Thread-12" java.lang.ArrayIndexOutOfBoundsException: 1
			this.delList.clear();
			for(String prop:tempString){
				this.delList.add(prop);
			}
		}else{this.delList=null;}
		
		tempString=behaviorPropositionString.split("<ADDLIST>");
		if((tempString!=null)&&(tempString.length>1)){
			tempString=tempString[1].substring(0, tempString[1].length()-1).split(",");
			this.addList.clear();
			for(String prop:tempString){
				this.addList.add(prop);
			}	

		}else{this.addList=null;}
		
		
		tempString=behaviorPropositionString.split("<ACTIONLIST>");
		if((tempString!=null)&&(tempString.length>1)){
			tempString=tempString[1].substring(0, tempString[1].length()-1).split(",");
			this.actionList.clear();
			for(String prop:tempString){
				this.actionList.add(prop);
			}		
		}else{this.actionList=null;}
		
		
		
	}

	private void createBehaviorPropositionString() {
		behaviorPropositionString="";
		behaviorPropositionString=behaviorPropositionString+"<NAME>"+this.getName()+"<NAME>"; //TODO THis is not standard XML
		behaviorPropositionString=behaviorPropositionString+"<PERFORMED>"+String.valueOf(performed)+"<PERFORMED>";
		
		if((addList!=null)&&(!addList.isEmpty())){
		behaviorPropositionString=behaviorPropositionString+"<ADDLIST>";
		for(String add:addList){
			behaviorPropositionString=behaviorPropositionString+add+",";
		}
		behaviorPropositionString=behaviorPropositionString+"<ADDLIST>";
		}
		
		if((delList!=null)&&(!delList.isEmpty())){
		behaviorPropositionString=behaviorPropositionString+"<DELLIST>";
		for(String del:delList){
			behaviorPropositionString=behaviorPropositionString+del+",";
		}
		behaviorPropositionString=behaviorPropositionString+"<DELLIST>";
		}

		if((actionList!=null)&&(!actionList.isEmpty())){
			behaviorPropositionString=behaviorPropositionString+"<ACTIONLIST>";
			for(String action:actionList){
				behaviorPropositionString=behaviorPropositionString+action+",";
			}
			behaviorPropositionString=behaviorPropositionString+"<ACTIONLIST>";
		}
		
	}

	/**
	 * @param behaviorPropositionString the behaviorPropositionString to set
	 */
	public synchronized void setBehaviorPropositionString(String behaviorPropositionString) {
		this.behaviorPropositionString = behaviorPropositionString;
		createBehaviorPropositions();//updates the variables based on the new string
	}
	
	public synchronized String getBehaviorPropositionString(){
		return this.behaviorPropositionString;
	}


	public synchronized String getName() {
		return this.name;
	}

	/**
	 * @return performed if the bp was performed or not
	 */
	public boolean isPerformed() {
		return performed;
	}
	/**
	 * @param used the used to set
	 */
	public void setPerformed(boolean performed) {
//		System.out.println("##### Trying to set performed to "+performed+"  ########");
//		System.out.println("Before: performed = "+this.performed+"  string: "+this.behaviorPropositionString);
		
		this.performed = performed;
		createBehaviorPropositionString(); //Updates the variables in the string

//		System.out.println("After: performed = "+this.performed+"  string: "+this.behaviorPropositionString);
//
//		System.out.println("#######################################");
	}
	/**
	 * @return the addList
	 */
	public List<String> getAddList() {
		return this.addList;
	}
	/**
	 * @param addList the addList to set
	 */
	public void setAddList(ArrayList<String> addList) {
		this.addList = addList;
	}
	/**
	 * @return the delList
	 */
	public List<String> getDelList() {
		return delList;
	}
	/**
	 * @param delList the delList to set
	 */
	public void setDelList(ArrayList<String> delList) {
		this.delList = delList;
	}
	/**
	 * @param name the name to set
	 */
	public synchronized void setName(String name) {
		this.name = name;
	}

}