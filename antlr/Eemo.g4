grammar Eemo;

discussion
    :   conversation
    ;

conversation
    :   ConversationStart
        statement*
        ConversationEnd
    ;

statement
    :   Increment
    |   Double
    |   Add
    |   Decrement
    |   Halve
    |   Sub
    |   Reset
    |   MoveToLeft
    |   MoveToRight
    |   MoveToRegister
    |   MoveToStack
    |   CopyToLeftStack
    |   Output
    |   Input
    |   ifPositive
    |   whilePositive
    |   Nop
    |   conversation
    ;

ifPositive
    :   IfPositive conversation
    ;

whilePositive
    :   WhilePositive conversation
    ;


ConversationStart:      '(^-^)/';
ConversationEnd:        '\\(._.)';
Increment:              '(^-^)';
Double:                 '(^o^)';
Add:                    '(*-*)';
Decrement:              '(;-;)';
Halve:                  '(;o;)';
Sub:                    '(._.)';
Reset:                  '(^~^)';
MoveToLeft:             '(°-° )';
MoveToRight:            '( °-°)';
MoveToRegister:         '\\(^-^)/';
MoveToStack:            '(>-<)';
CopyToLeftStack:        '(° _° )';
Output:                 '(°o°)';
Input:                  '(o-o)';
IfPositive:             '(?-?)';
WhilePositive:          '(+-+)';
Nop:                    '(-_-)';

Id
    :   '(' ~(' ' | '(' | ')')+ ')'
    ;

Skip
    :   ~[(\\] -> skip
    ;