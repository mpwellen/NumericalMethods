// gauleg.java     P145 Numerical Recipes in Fortran               
// compute x(i) and w(i)  i=1,n  Legendre ordinates and weights 
// on interval  -1.0 to 1.0 (length is 2.0)                     
// use ordinates and weights for Gauss Legendre integration     

public class gauleg
{
  public gauleg()
  {
    double sum, a, b;
    double x[] = new double[100];
    double w[] = new double[100];

    System.out.println("test gauleg.java on interval -1.0 to 1.0  ordinates, weights");
    for(int i=1; i<=15; i++)
    {
      gaulegf(-1.0, 1.0, x, w, i);
      sum = 0.0;
      for(int j=1; j<=i; j++)
      {
        System.out.println("x["+j+"]="+x[j]+", w["+j+"]="+w[j]);
        sum = sum + w[j];
      }
      System.out.println("              integral(1.0, -1.0..1.0)="+sum);
      System.out.println("");
    }

    a = 0.5;
    b = 1.0;
    for(int i=2; i<=10; i++)
    {
      gaulegf(a, b, x, w, i);
      sum = 0.0;
      for(int j=1; j<=i; j++)
      {
        sum = sum + w[j]*Math.sin(x[j]);
      }
      System.out.println("integral (0.5,1.0) sin(x) dx = "+sum);
    }
    System.out.println("-cos(1.0)+cos(0.5) = "+ (-Math.cos(1.0)+Math.cos(0.5)));
    System.out.println("Maple says 0.3372802560 ");
    System.out.println("");

    a = 0.5;
    b = 5.0;
    for(int i=2; i<=10; i++)
    {
      gaulegf(a, b, x, w, i);
      sum = 0.0;
      for(int j=1; j<=i; j++)
      {
        sum = sum + w[j]*Math.exp(x[j]);
      }
      System.out.println("integral (0.5,5.0) exp(x) dx = "+sum);
    }
    System.out.println("exp(5.0)-exp(0.5) = "+(Math.exp(5.0)-Math.exp(0.5)));
    System.out.println("Maple says 146.7644378 ");
    System.out.println("");

    a = 0.5;
    b = 5.0;
    for(int i=2; i<=30; i++)
    {
      gaulegf(0.5, 5.0, x, w, i);
      sum = 0.0;
      for(int j=1; j<=i; j++)
      {
        sum = sum + w[j]*((Math.pow(Math.pow(x[j],x[j]),x[j])*
                          (x[j]*(Math.log(x[j])+1.0)+x[j]*Math.log(x[j]))));
      }
      System.out.println("integral (0.5,5.0) mess(x) dx = "+sum);
    }
    System.out.println("((5.0**5.0)**5.0)-(0.5**0.5)**0.5 = "+
           (Math.pow(Math.pow(5.0,5.0),5.0)-Math.pow(Math.pow(0.5,0.5),0.5)));
    System.out.println("Maple says 2.980232239E17 ");
    System.out.println(" ");
    System.out.println("Done.");
  } // end gauleg 

  void gaulegf(double x1, double x2, double x[], double w[], int n)
  {
    int m;
    double eps = 3.0E-14;
    double p1, p2, p3, pp, xl, xm, z, z1;
  
    m = (n+1)/2;
    xm = 0.5*(x2+x1);
    xl = 0.5*(x2-x1);
    for(int i=1; i<=m; i++)
    {
      z = Math.cos(3.141592654*((double)i-0.25)/((double)n+0.5));
      while(true)
      {
        p1 = 1.0;
        p2 = 0.0;
        for(int j=1; j<=n; j++)
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
  } // end gaulegf 

  public static void main (String[] args)
  {
    new gauleg();
  }
} // end gauleg.java
