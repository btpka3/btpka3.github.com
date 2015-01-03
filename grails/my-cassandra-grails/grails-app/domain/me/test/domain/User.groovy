package me.test.domain

class User {

	static constraints = {
	}
	static mapping = {
		table "xxx"
		id name : "id",primaryKey : [ordinal : 0, type : "partitioned"], generator : "assigned"
		sid primaryKey : [ordinal : 1, type : "clustered"]
	}

	String id;

	String sid;

	String name;

	Set<String> tags;

	List<String> addrs;

	Map<String, String> extra;

	String memo;
}
