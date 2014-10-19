lexeme = ['']*100
Letter = 0
Digit =1
Unknown = 99

lexLen = 0
nextChar = ' '
EOF = -1
charClass = -2
nextToken = -3


Int_lit = 10
Ident = 11
Assign_op = 20
Add_op = 21
Sub_op = 22
Mult_op = 23
Div_op = 24
Left_paren = 25
Right_paren = 26

charList = []
index =0

# defining function addChar()
def addChar():
    global lexLen
    if lexLen <=98:
        lexeme[lexLen] = str(nextChar)
        lexLen=lexLen+1
        lexeme[lexLen]=0
    else:
        print("Error: lexeme is too long")

# defining function getChar()
def getChar():
    global index
    global charClass
    global nextChar
    if index < len(charList):
        nextChar = charList[index]
        index = index + 1
        if nextChar.isalpha():
            charClass = Letter
        elif nextChar.isdigit():
            charClass = Digit
        else:
            charClass = Unknown
    else:
        charClass = EOF

# defining function getNonBlank()
def getNonBlank():
    while nextChar.isspace():
        getChar()

# defining function lookup()
def lookup (ch):
    global nexToken
    if ch == '(':
        addChar()
        nextToken = Left_paren
    elif ch == ')':
        addChar()
        nextToken = Right_paren
    elif ch == '+':
        addChar()
        nextToken = Add_op
    elif ch == '-':
        addChar()
        nextToken = Sub_op
    elif ch == '*':
        addChar()
        nextToken = Mult_op
    elif ch == '/':
        addChar()
        nextToken = Div_op
    else:
        addChar()
        nextToken = EOF

# defining funciton lex()
def lex():
    lexterm = ' '
    global lexLen
    global nextToken
    lexLen = 0
    getNonBlank()
    if charClass == Letter:
        addChar()
        getChar()
        while charClass == Letter or charClass == Digit:
            addChar()
            getChar()
        nextToken = Ident
    elif charClass == Digit:
        addChar()
        getChar()
        while charClass == Digit:
            addChar()
            getChar()
        nextToken = Int_lit
    elif charClass == Unknown:
        lookup(nextChar)
        getChar()
    else:
        nextToken = EOF
        lexeme[0] = 'E'
        lexeme[1] = 'O'
        lexeme[2] = 'F'
        lexeme[3] = 0
    for each_char in lexeme:
        if  isinstance(each_char, str):
            lexterm += each_char       
    print("Next token is "+ str(nextToken)+ ', '+"Next lexeme is "+lexterm)
    
try:
    with open ('testfile.txt')as file:
        while True:
            c = file.read(1)
            if not c:
                break
            else:
                charList.append(c)
except IOException:
    print("file does not exist")
getChar()
lex()
while nextToken != EOF:
    lex()

        
    

        
