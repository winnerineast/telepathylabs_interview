package models;

public class Node {
    //Required field.
    public Node[] Children;
    public Node Right;

    //Indicated
    public String Name;
    public int iX;
    public int iY;

    public Node(String strName){
        this.Name = strName;
        this.Children = null;
        this.Right = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int iNoofChildren = 0;
        if (this.Children==null)
            iNoofChildren = 0;
        Node nRight = this.Right;
        if (nRight==null)
            nRight = new Node("NULL");
        sb.append(this.Name).append(":").append(Integer.toString(iNoofChildren)).append(":").append(nRight.Name);
        sb.append(":(").append(Integer.toString(this.iX)).append(",").append(Integer.toString(this.iY)).append(")");
        return sb.toString();
    }
}
