/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jvscanner;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
public class JVscanner {
    
        static int index = 0;
        static char[] outchar;
        static int charClass;
        static char [] lexeme = new char[100];
        static char nextChar;
        static int lexLen;
        static int token;
        static int nextToken;
                
        static final int Letter = 0;
        static final int Digit = 1;
        static final int Unknown = 99;
        
        static final int Int_lit = 10;
        static final int Ident =11;
        static final int Assign_op = 20;
        static final int Add_op = 21;
        static final int Sub_op = 22;
        static final int Mult_op = 23;
        static final int Div_op = 24;
        static final int Left_paren = 25;
        static final int Right_paren = 26;
        static final int EOF = -1;
        
    public static void main(String[] args) throws IOException{
        List<String> exp = new ArrayList();
        getFile(exp);      
        outchar = toChars(exp);
        getChar(outchar);
        do
        {
            lex(outchar);
        }while (nextToken != EOF);
    }
    
    
    public static void getFile(List<String> ls)
    {
        File file = new File("joefile.txt");
        String line;
        if (!file.exists())
            System.out.print("file does not exist.");
        else{
            try
            {
               BufferedReader reader = new BufferedReader(new FileReader(file));
               line = reader.readLine();
               while (line !=null){
                   ls.add(line);
                   line = reader.readLine();
               }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public static char[] toChars(List<String> ls)
    {
        char[] temp;
        String lines=" ";
        for (String l : ls) 
            lines += l;            
        temp = lines.toCharArray();
        return temp;
    }
    
    public static void getChar(char[] ca)
    {
        if (index < ca.length)
        {
            nextChar = ca[index++];
            if (Character.isLetter(nextChar))
                charClass = Letter;               
            else if (Character.isDigit(nextChar))
                charClass = Digit;
            else
                charClass = Unknown;
        }
        else
            charClass = EOF;
    }
    
    public static void addChar()
    {
        if (lexLen <=98)
        {
            lexeme[lexLen++]=nextChar;
            lexeme[lexLen]=0;
        }
        else
            System.out.println("Error:lexeme is too long.");    
    }
    
    public static void getNonBlank(char[] ca)
    {
        while (Character.isWhitespace(nextChar))
            getChar(ca);
    }
    
    public static int lookUp(char ch)
    {
        switch(ch)
        {
            case '(':
                addChar();
                nextToken = Left_paren;
                break;
            case ')':
                addChar();
                nextToken = Right_paren;
                break;
            case '+':
                addChar();
                nextToken = Add_op;
                break;
            case '-':
                addChar();
                nextToken = Sub_op;
                break;
            case '*':
                addChar();
                nextToken = Mult_op;
                break;
            case '/':
                addChar();
                nextToken = Div_op;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;                 
    }
    
    public static void lex(char[] ca)
    {
        String lexterm = "";
        lexLen = 0;
        getNonBlank(ca);
        switch(charClass)
        {
            case Letter:
                addChar();
                getChar(ca);
                while(charClass == Letter || charClass == Digit)
                {
                    addChar();
                    getChar(ca);
                }
                nextToken = Ident;
                break;
            case Digit:
                addChar();
                getChar(ca);
                while (charClass == Digit)
                {
                    addChar();
                    getChar(ca);
                }
                nextToken = Int_lit;
                break;
            case Unknown:
                lookUp(nextChar);
                getChar(ca);
                break;
            case EOF:
                nextToken = EOF;
                lexeme[0]= 'E';
                lexeme[1]= 'O';
                lexeme[2]= 'F';
                lexeme[3]= 0;                
        }
        for (char c : lexeme)      
           if (c != ' ')
               lexterm += c;       
        System.out.println("Next token is "+ nextToken + ", "+ "Next lexeme is " + lexterm);
        //return nextToken;
    }
}
