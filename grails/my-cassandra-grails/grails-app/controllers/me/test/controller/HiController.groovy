package me.test.controller

import me.test.domain.User

class HiController {

	def index() {
		render "OK"
	}

	def insert(){

		def num = 0

		def u1 = new User()
		u1.id = "4"
		u1.sid= "sid${num}"
		u1.name = "name${num}"
		u1.tags=["tag${num}.3", "tag${num}.4"].toSet()
		u1.addrs = [
			"addr${num}.3",
			"addr${num}.4"
		]
		u1.extra=[
			"key${num}.3" : "value${num}.3",
			"key${num}.4" : "value${num}.4"
		]
		u1.memo = "memo${num}"
		u1.save()
		render "inserted"
	}
	
	def select(){
		
		
		render "inserted"
	}
}
