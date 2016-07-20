interface Person {
    firstName:string;
    lastName:string;
}

class Student {
    fullName:string;

    constructor(public firstName:string, public middleInitial:string, public lastName:string) {
        this.fullName = firstName + " " + middleInitial + " " + lastName;
    }
}

function greeter(person:Person) {
    return `Hello, ${person.firstName} ${person.lastName}`;
}

var user = new Student("Jane", "M.", "User");

console.log(greeter(user));
