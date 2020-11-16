# Rating-Predictor

The objective of this assignment is to test your understanding of Collections. You will learn to work with ArrayLists, HashMaps and HashSets primarily. In this assignment, you will be implementing a version of the bag of words model used in Machine Learning. You will create a program that will be able to make a prediction of the ratings of movie reviews based on some learning it did beforehand using some reviews that already had ratings associated with them. Although it is one of the very basic primitive models, it gives you an introduction to the world of Machine Learning. 

In this assignment you’ll be doing the following:
- Read a raw text file containing movie reviews and ratings. 
- Read a file containing stopwords and create a HashSet which will be used for cleaning the data.
- Clean the data. (For example: Remove the punctuations and stopwords (e.g. is, and, the, etc.) from the reviews) and write the contents to a clean review file.
- Create a HashMap which associates a word from the cleaned file to values which represent the total rating and the count of the word in the given file. 
- Use the HashMap between the words and their associated values and rate reviews from another file that doesn’t have any rating already.
- Write the ratings of the reviews to an output file.

You are allowed to use built-in packages for this PA.
