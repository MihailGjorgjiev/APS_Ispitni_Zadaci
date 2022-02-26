import javax.naming.InsufficientResourcesException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DLLNode<E> {
    protected E element;
    protected DLLNode<E> pred, succ;

    public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
        this.element = elem;
        this.pred = pred;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return "<-"+element.toString()+"->";
    }
}


class DLL<E> {
    private DLLNode<E> first, last;

    public DLL() {
        // Construct an empty SLL
        this.first = null;
        this.last = null;
    }

    public void deleteList() {
        first = null;
        last = null;
    }

    public int length() {
        int ret;
        if (first != null) {
            DLLNode<E> tmp = first;
            ret = 1;
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret++;
            }
            return ret;
        } else
            return 0;

    }

    public void insertFirst(E o) {
        DLLNode<E> ins = new DLLNode<E>(o, null, first);
        if (first == null)
            last = ins;
        else
            first.pred = ins;
        first = ins;
    }

    public void insertLast(E o) {
        if (first == null)
            insertFirst(o);
        else {
            DLLNode<E> ins = new DLLNode<E>(o, last, null);
            last.succ = ins;
            last = ins;
        }
    }

    public void insertAfter(E o, DLLNode<E> after) {
        if(after==last){
            insertLast(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }

    public void insertBefore(E o, DLLNode<E> before) {
        if(before == first){
            insertFirst(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
    }

    public E deleteFirst() {
        if (first != null) {
            DLLNode<E> tmp = first;
            first = first.succ;
            if (first != null) first.pred = null;
            if (first == null)
                last = null;
            return tmp.element;
        } else
            return null;
    }

    public E deleteLast() {
        if (first != null) {
            if (first.succ == null)
                return deleteFirst();
            else {
                DLLNode<E> tmp = last;
                last = last.pred;
                last.succ = null;
                return tmp.element;
            }
        }
        // else throw Exception
        return null;
    }
    public E delete(DLLNode<E> node) {
        if(node==first){
            deleteFirst();
            return node.element;
        }
        if(node==last){
            deleteLast();
            return node.element;
        }
        node.pred.succ = node.succ;
        node.succ.pred = node.pred;
        return node.element;

    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            DLLNode<E> tmp = first;
            ret += tmp + "<->";
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += tmp + "<->";
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public DLLNode<E> getFirst() {
        return first;
    }

    public DLLNode<E> getLast() {

        return last;
    }

}

public class DLLVojska {


    public static void main(String[] args) throws IOException {
        DLL<Integer> lista = new DLL<Integer>();
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s = stdin.readLine();
        int N = Integer.parseInt(s);
        s = stdin.readLine();
        String[] ids = s.split(" ");
        for (int i = 0; i < N; i++) {
            lista.insertLast(Integer.parseInt(ids[i]));
        }

        s = stdin.readLine();
        String interval[] = s.split(" ");
        int a = Integer.parseInt(interval[0]);
        int b = Integer.parseInt(interval[1]);

        s = stdin.readLine();
        interval = s.split(" ");
        int c = Integer.parseInt(interval[0]);
        int d = Integer.parseInt(interval[1]);


        DLL<Integer> result = vojska(lista, a, b, c, d);


        DLLNode<Integer> node = result.getFirst();
        System.out.print(node.element);
        node = node.succ;
        while(node != null){
            System.out.print(" "+node.element);
            node = node.succ;
        }

    }

    private static DLL<Integer> vojska(DLL<Integer> lista, int a, int b, int c, int d) {

        boolean begin=false,end=false;
        DLLNode<Integer> i1;
//        DLLNode<Integer> i2= lista.getFirst();
//        DLLNode<Integer> j1= lista.getFirst();
//        DLLNode<Integer> j2= lista.getFirst();

        DLL<Integer> tmplistFull=new DLL<Integer>();
        DLL<Integer> tmplistOne=new DLL<Integer>();
        DLL<Integer> tmplistMid=new DLL<Integer>();
        DLLNode<Integer> curr= lista.getFirst();
        while (curr.element!=a){
            tmplistFull.insertLast(curr.element);
            curr=curr.succ;
        }
        while (curr.element!=b){
            tmplistOne.insertLast(curr.element);
            curr=curr.succ;
        }
        tmplistOne.insertLast(curr.element);
        curr=curr.succ;
        while(curr.element!=c){
            tmplistMid.insertLast(curr.element);
            curr=curr.succ;
        }
        while (curr.element!=d){
            tmplistFull.insertLast(curr.element);
            curr=curr.succ;
        }
        tmplistFull.insertLast(curr.element);
        curr=curr.succ;

        i1=tmplistMid.getFirst();
        while (tmplistMid.length()>0 && i1!=null){
            tmplistFull.insertLast(i1.element);
            i1=i1.succ;
        }
        i1=tmplistOne.getFirst();
        while (tmplistOne.length()>0 && i1!=null){
            tmplistFull.insertLast(i1.element);
            i1=i1.succ;
        }
        while (curr!=null){
            tmplistFull.insertLast(curr.element);
            curr=curr.succ;
        }
        lista=tmplistFull;
//        int n1=1;
//        int n2=1;
//        while (i1.element!=a){
//            i1=i1.succ;
//        }
//        i2=i1;
//        while (i2.element!=b){
//            i2=i2.succ;
//            n1++;
//        }
//        while (j1.element!=c){
//            j1=j1.succ;
//        }
//        j2=j1;
//        while (j2.element!=d){
//            j2=j2.succ;
//            n2++;
//        }
//
//            i2=i2.succ;
//        while (n1>0){
//            i1=i2.pred;
//            int temp= lista.delete(i1);
//            lista.insertBefore(temp,j1);
//            n1--;
//        }
//        j1=j1.pred;
//        while (n2>0){
//            j1=j2.pred;
//            int temp= lista.delete(j1);
//            lista.insertBefore(temp,i2);
//            i2=i2.pred;
//            n2--;
//        }







        return lista;
    }
}