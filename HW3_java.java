// test_gauleg.java

public class test_gauleg
{
public gaulegf(double x1, double x2, double x[], double w[], int n)
  {
    int i, j, m;
    double eps = 3.0E-14;
    double p1, p2, p3, pp, xl, xm, z, z1;
  
    m = (n+1)/2;
    xm = 0.5*(x2+x1);
    xl = 0.5*(x2-x1);
    for(i=1; i<=m; i++)
    {
      z = Math.cos(3.141592654*((double)i-0.25)/((double)n+0.5));
      while(true)
      {
        p1 = 1.0;
        p2 = 0.0;
        for(j=1; j<=n; j++)
        {
          p3 = p2;
          p2 = p1;
          p1 = ((2.0*(double)j-1.0)*z*p2-((double)j-1.0)*p3)/
                (double)j;
        }
        pp = (double)n*(z*p1-p2)/(z*z-1.0);
        z1 = z;
        z = z1 - p1/pp;
        if(Math.abs(z-z1) <= eps) break;
      }
      x[i] = xm - xl*z;
      x[n+1-i] = xm + xl*z;
      w[i] = 2.0*xl/((1.0-z*z)*pp*pp);
      w[n+1-i] = w[i];
    }
  } // end gaulegf constructor 
  public test_gauleg()
  {
    double area;
    double a = 1.0;
    double b = 2.0;
    int n = 8;
    int m;
    double x[] = new double[n+1];
    double w[] = new double[n+1];

    System.out.println("test gauleg.java integrate fm(p)=p^m from a=1 to b=2");
    for(m=2; m<=4; m++)
    {
      System.out.println("f2 on "+m+" points");
      new gaulegf(a, b, x, w, m);
      System.out.print("x="+x[1]+" "+x[2]);
      for(int j=3; j<=m; j++) System.out.print(" "+x[j]);
      System.out.println(" ");
      System.out.print("w="+w[1]+" "+w[2]);
      for(int j=3; j<=m; j++) System.out.print(" "+w[j]);
      System.out.println(" ");

      area = 0.0;
      for(int i=1; i<=m; i++)
      {
        area += w[i]*f2(x[i]);
      }
      System.out.println("f2 exact area=7/3 computed area = "+area);
      System.out.println("error= "+(area-7.0/3.0));
      System.out.println(" ");
    } // end m on f2
    for(m=2; m<=4; m++)
    {
      System.out.println("f3 on "+m+" points");
      new gaulegf(a, b, x, w, m);
      area = 0.0;
      for(int i=1; i<=m; i++)
      {
        area += w[i]*f3(x[i]);
      }
      System.out.println("f3 exact area=15/4 computed area = "+area);
      System.out.println("error= "+(area-15.0/4.0));
      System.out.println(" ");
    } // end m on f3

    for(m=2; m<=4; m++)
    {
      System.out.println("f4 on "+m+" points");
      new gaulegf(a, b, x, w, m);
      area = 0.0;
      for(int i=1; i<=m; i++)
      {
        area += w[i]*f4(x[i]);
      }
      System.out.println("f4 exact area=31/5 computed area = "+area);
      System.out.println("error= "+(area-31.0/5.0));
      System.out.println(" ");
    } // end m on f4

    for(m=2; m<=4; m++)
    {
      System.out.println("f5 on "+m+" points");
      new gaulegf(a, b, x, w, m);
      area = 0.0;
      for(int i=1; i<=m; i++)
      {
        area += w[i]*f5(x[i]);
      }
      System.out.println("f5 exact area=63/6 computed area = "+area);
      System.out.println("error= "+(area-63.0/6.0));
      System.out.println(" ");
    } // end m on f5

    for(m=2; m<=4; m++)
    {
      System.out.println("f6 on "+m+" points");
      new gaulegf(a, b, x, w, m);
      area = 0.0;
      for(int i=1; i<=m; i++)
      {
        area += w[i]*f6(x[i]);
      }
      System.out.println("f6 exact area=127/7 computed area = "+area);
      System.out.println("error= "+(area-127.0/7.0));
      System.out.println(" ");
    } // end m on f6

  }

  double f2(double p) // function to integrate
  {
    return p*p;
  }

  double f3(double p) // function to integrate
  {
    return p*p*p;
  }

  double f4(double p) // function to integrate
  {
    return p*p*p*p;
  }

  double f5(double p) // function to integrate
  {
    return p*p*p*p*p;
  }

  double f6(double p) // function to integrate
  {
    return p*p*p*p*p*p;
  }

  public static void main (String[] args)
  {
    new test_gauleg();
  }
} // end test_gauleg.java

