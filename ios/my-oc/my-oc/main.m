

#import <Foundation/Foundation.h>
#import "MyDog.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // 声明 NSObject 子类型的变量时，可以使用 id 作为变量类型，
        // 但这回失去编译器静态类型判断的能力，无法提前排查一些错误，故不推荐。
        NSNumber *myLong1 = @42L;
        NSNumber *myLong2 = @42L;
        NSNumber *myLong3 = @42;
        
        NSLog(@"Hello, World! myLong1 :%@, myLong3:%@,  %i, %i, %i",
              myLong1,myLong3,
              myLong1 == myLong2,
              myLong1 == myLong3,
              [myLong1 isEqual:myLong3]);
        
        // 方法调用是用中括号[ ]
        
        // 开辟内存空间
        MyDog* dog = [MyDog alloc];
        // 实例初始化。
        dog = [dog init];
        // 上面两句通常可以将两句合并成一句
        // MyDog* dog = [[MyDog alloc] init];
        // 或者替换为使用 new
        // MyDog* dog = [MyDog new];
        
        
        dog->id = 10;       // 直接访问成员变量
        [dog setAge:27];    // 方法调用
        
        // 作用同上，调用 getter、setter，
        // 只有 getter，setter 才能使用 "." 操作符
        // "." 操作符本质上是方法调用，因此无参数的方法，
        // 也可以使用 "." 操作符来调用，只不过，不接收返回值时，会有编译警告。
        dog.age = 28;
        
        int age = [dog age];
        age = dog.age;
        
        NSLog(@"dog is %i and age is %i.", dog->id, age);
        
        MyDog* dog1 = [[MyDog alloc] initWithAge:9];
        NSLog(@"dog1's description is %@.", dog1);
        
        dog1.color = @"red";
        [dog1 speak];
        // dog1.speak;  // OK
        
        // 销毁对象
        // 因为启用了 ARC （Automatic Reference Counting，自动引用计数），
        // 故无需手动调用 retain, release和autorelease，
        // 但仅仅对继承了 NSObject 的对象有用，更底层的仍需自己手动管理内存。
        // [dog release];
    }
    return 0;
}
