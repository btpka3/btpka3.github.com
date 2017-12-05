

#import "MyDog.h"

@implementation MyDog

// getter
- (int) age {
    return age;
}

// setter
- (void) setAge: (int) newAge {
    age = newAge;
}


// 自定义构造函数
- (id)initWithAge:(int) newAge {
    self = [super init];
    if (self != nil) {  // 可以简化为 "if(self)"
        age = newAge;
    }
    return self;
}


// 重写description方法
- (NSString *)description {
    
    // 可以通过 super关键字调用父类上的实现方法。
    return [NSString stringWithFormat:@"MyDog(id=%i, age=%i, color=%@, d=%@)",
            id, age, _color, [super description]];
}



- (void) speak{
    NSLog(@"%@ speak 'Wang' ~", self);
}

@end
