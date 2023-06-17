public class REcompile{
private static String regex;

    public static void main(String[] args){
        //initialise vartiables
        regex = args[0];
        currIndex = 0;

        try
        {
            Node fsm = parse();
            // TODO: Generate FSM representation from the parsed nodes
            // ...
        } 
        catch(Exception e)
        {
            System.out.println("Error parsing: " + e.getMessage());
        }
    }


    private static Node parse(){
        Node node = parseCon();

        //If the char in the index is '|' then
        while(currIndex < regex.length() && regex.charAt(currentIndex) == '|') 
        {
            currIndex++;
            Node rightNode = parseCon();
            node = new altNode(node, rightNode);
        }

        return node;
    }

    private static Node parseCon() {
        Node node = parseRep();

        while (currentIndex < regex.length() && regex.charAt(currentIndex) != '|' && regex.charAt(currentIndex) != ')') {
            Node rightNode = parseRep();
            node = new ConcatenationNode(node, rightNode);
        }

        return node;
    }

    private static Node parseRep() {
        Node node = parseBase();

        if (currentIndex < regex.length() && regex.charAt(currentIndex) == '*') {
            currentIndex++; 
            node = new ClosureNode(node);
        } else if (currentIndex < regex.length() && regex.charAt(currentIndex) == '+') {
            currentIndex++; 
            node = new PositiveClosureNode(node);
        } else if (currentIndex < regex.length() && regex.charAt(currentIndex) == '?') {
            currentIndex++; 
            node = new OptionalNode(node);
        }

        return node;
    }

    