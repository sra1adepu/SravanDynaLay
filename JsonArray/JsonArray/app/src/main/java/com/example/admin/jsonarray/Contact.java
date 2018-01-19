package com.example.admin.jsonarray;

/**
 * Created by admin on 11-05-2017.
 */

public class Contact {

    //private variables
    int id;
    String relationtype;
    String name;
    String mobileno;
    String address;
    String occupation;
    String anyproof;
    String category;

    public Contact()
    {

    }
    // Empty constructor
    public Contact(int id, String relationtype, String name, String mobileno, String address, String occupation,String anyproof,String category ){
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.address=address;
        this.occupation=occupation;
        this.anyproof=anyproof;
        this.relationtype=relationtype;
        this.category=category;
    }



    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    public String getCategory(){

        return this.category;
    }

    // setting id
    public void setCategory(String category){

        this.category = category;
    }

    // getting name
    public String getName(){
        return this.name;
    }

    // setting name
    public void setName(String name){
        this.name = name;
    }

    // getting phone number
    public String getMobileno(){
        return this.mobileno;
    }

    // setting phone number
    public void setMobileno(String mobileno){
        this.mobileno = mobileno;
    }
    public void setRelationtype(String relationtype){
        this.relationtype = relationtype;
    }
    public String getRelationtype(){
        return this.relationtype;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setOccupation(String occupation){
        this.occupation = occupation;
    }
    public String getOccupation(){
        return this.occupation;
    }
    public void setAnyproof(String anyproof){
        this.anyproof = anyproof;
    }
    public String getAnyproof(){
        return this.anyproof;
    }

}