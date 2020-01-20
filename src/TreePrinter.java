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

        for(Node child: this.root.getChildren()){
            TreePrinter childPrinter = new TreePrinter(child);
            if(!child.getChildren().isEmpty()){
                childPrinter.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }else {
                childPrinter.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }

    }

}
