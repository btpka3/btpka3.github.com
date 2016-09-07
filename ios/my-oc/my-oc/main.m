

#import <Foundation/Foundation.h>
#import "MyDog.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // insert code here...
        NSLog(@"Hello, World!");
        
        // 方法调用是用中括号[ ]
        
        // 开辟内存空间
        MyDog* dog = [MyDog alloc];
        // 实例初始化。
        dog = [dog init];
        // 上面两句通常可以将两句合并成一句
        // MyDog* dog = [[MyDog alloc] init];
        // 或者替换为使用 new
        // MyDog* dog = [MyDog new];
        
        
        dog->id = 10;
        [dog setAge:27];
        int age = [dog age];
        
        NSLog(@"dog is %i and age is %i", dog->id, age);
        
        // 销毁对象
        // 因为启用了 ARC （Automatic Reference Counting，自动引用计数），
        // 故无需手动调用 retain, release和autorelease，
        // [dog release];
    }
    return 0;
}
