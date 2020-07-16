/*
Complete the solution so that it strips all text that follows any of a set of comment markers passed in. Any whitespace at the end of the line should also be stripped out.

Example:

Given an input string of:

apples, pears # and bananas
grapes
bananas !apples
The output expected would be:

apples, pears
grapes
bananas
The code would be called like so:

var result = solution("apples, pears # and bananas\ngrapes\nbananas !apples", ["#", "!"])
// result should == "apples, pears\ngrapes\nbananas"
 */
public class StripComments {

    public static String stripComments(String text, String[] commentSymbols) {

        for (String symbol : commentSymbols) {
            int index = text.indexOf(symbol);
            while (index != -1) {
                int endIndex = text.indexOf("\n", index + 1);
                if (endIndex != -1) {
                    text = text.substring(0, index) + text.substring(endIndex);
                } else {
                    text = text.substring(0, index);
                }

                index = text.indexOf(symbol);
            }
        }
        text = clearWhiteSpaces(text);

        return rtrim(text);
    }

    public static String clearWhiteSpaces(String text) {
        int index = text.indexOf("\n");
        while (index != -1) {
            text = rtrim(text.substring(0, index)) + text.substring(index);
            index = text.indexOf("\n", index + 1);
        }

        return text;
    }

    public static String rtrim(String text) {
        text = text.replaceAll("[ ]+$", "");
        return text;
    }
}