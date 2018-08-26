package KM;

public class KM
{
    public static void main(String[] args)
    {
        System.out.println(args.length);

        if(args.length < 1)
        {
            System.out.println("Usage: KM [input.txt]");
            return;
        }
        String inputFile = args[0];
        System.out.println((inputFile));

    }

}
