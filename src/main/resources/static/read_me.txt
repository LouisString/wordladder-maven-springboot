*Note:
    1. getWordlist.java is to require file path and build dictionary using class in readfile.java;
    2. readFile.java is a class that will build and return dictionary from the given file;
    3. inquireWords.java is to require 2 words from the user.
    4. buildLadder.java is a class that is used to build word ladder between given words;
    5. userInterface.java is the main class that uses above classes.

    This is the 3rd version, which executes in satisfactory speed with a more friendly interface and using Maven and Junit.

*Spring boot version:
    Please input a URL in the following URI form:
        http://localhost:8080/wordsladder/?word1=firstword&word2=secondword
    where firstword and secondword are defined by the user.

*Login:
    This is the first version that involves logging in.
    The user has to type a URL in the following URI form:
        http://localhost:8080/login/?usn=username&pwd=password
    before they can type
        http://localhost:8080/wordsladder/?usn=username&word1=firstword&word2=secondword
    to get the ladder.

    After login, if the user doesn't require to build a ladder in 5 minutes (300 seconds),
    his/her login status will be discarded, which means he/she has to relogin. However,
    if he/she requires it, then his/her login status will be renewed and 5 minutes' life
    circle starts over again.