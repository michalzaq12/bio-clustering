public class TreePrinter {

    private Node root;

    public TreePrinter(Node root) {
        this.root = root;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.root.getLabelWithDistance());
        buffer.append('\n');

        Node child1 = this.root.getChild1();
        if(child1 != null){
            TreePrinter child1Printer = new TreePrinter(child1);
            if(child1.getChild1() != null){
                child1Printer.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }else {
                child1Printer.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }


        Node child2 = this.root.getChild2();
        if(child2 != null){
            TreePrinter child2Printer = new TreePrinter(child2);
            if(child2.getChild1() != null){
                child2Printer.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }else {
                child2Printer.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

}
