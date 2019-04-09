// Legendre.java  Legendre polynomials and quadrature
//
//  P0(x)=1  P1(x)=x  Pn(x)=x*((2n-1)/n)*Pn-1(x) - ((n-1)/n)*Pn-2(x)
//              or  Pn+1(x)=x*)((2n+1)/(n+1))Pn(x)- (n/(n+1))Pn-1(x)
//  P2(x)=3/2 x^2 - 1/2   P3(x)=5/2 x^3 - 3/2 x  P4(x)=35/8 x^4 -30/8 x^2 + 3/8
//  int -1 to 1 of f(x)dx = sum i=1,n  w[i] F(x[i])
//  w[i]= -2/((n+1)P'n(x[i])Pn+1(x[i]))
//  x[i]= roots of Pn(x)
//
//  n=2 x= +/- .5773502692 w=1.0
//  n=3 x= +/- .7745966692 w= .5555555556
//            0.0             .8888888889
//  n=4 x= +/- .8611363116 w= .3478546451
//         +/- .3399810436    .6521451549
//  n=5 x= +/- .9061798459    .2369268851
//         +/- .5384693101    .4786286705
//            0.0             .5688888889
//
// conversion/scaling  int a to b of f(x)dx =
//             (b-a)/2 int -1 to 1 of f((a+b+x*(b-a))/2)dx
//
// Gauss-Legendre quadrature, integrate f(x) from -1 to 1
//   int -1 to 1 f(x) dx = sum i=0,n-1 w[i]f(x[i])

package myjava;
import java.awt.*;
import java.awt.event.*;
import myjava.*;

public class Legendre extends Frame
{
  int N=15; // change to max polynomial size
  double P[][] = new double[N+1][N+1]; // coefficients of Pn(x)
  double DP[][] = new double[N][N]; // derivitives of Pn(x)
  double R[][] = new double[N][N]; // roots of Pn(x) the x[i]
  double W[][] = new double[N][N]; // weights of Pn(x) the w[i]

  public Legendre()
  {
    System.out.println("Legendre.java running");
    // generate family of Legendre polynomials Pn(x)
    // as P[degree][coefficients]
    // build coefficients of Pn(x)
    for(int n=0; n<N+1; n++) for(int i=0; i<N; i++) P[n][i]=0.0;
    P[0][0]=1.0;
    P[1][1]=1.0; // start for recursion
    for(int n=2; n<N+1; n++)
    {
      for(int i=0; i<=n; i++)
      {
        if(i<n-1) P[n][i]=-((n-1)/(double)n)*P[n-2][i];
        if(i>0)   P[n][i]=P[n][i]+((2*n-1)/(double)n)*P[n-1][i-1];
        System.out.println("P["+n+"]["+i+"]="+P[n][i]);
      }
      System.out.println();
    }

    // compute derivitives of Pn(x)
    for(int n=0; n<N; n++) for(int i=0; i<N; i++) DP[n][i]=0.0;
    for(int n=1; n<N; n++)
    {
      for(int i=0; i<n; i++)
      {
        DP[n][i]=(double)(i+1)*P[n][i+1];
        System.out.println("DP["+n+"]["+i+"]="+DP[n][i]);
      }
      System.out.println();
    }

    // find roots of Pn(x), the x[i]
    for(int n=0; n<N; n++) for(int i=0; i<N; i++) R[n][i]=0.0;
    R[1][0]=1.0;
    for(int n=2; n<N; n++)
    {
      LRoot(n, P, R);
      for(int i=0; i<n; i++)
      {
        System.out.println("R["+n+"]["+i+"]="+R[n][i]);
      }
      System.out.println();
    }

    // compute weights of Pn(x)
    double sumw=0.0;
    for(int n=0; n<N; n++) for(int i=0; i<N; i++) W[n][i]=0.0;
    W[1][0]=1.0;
    for(int n=2; n<N; n++)
    {
      sumw = 0.0;
      for(int i=0; i<n; i++)
      {
        W[n][i]=-2.0/((n+1)*Evaluate(n, DP[n], R[n][i])*
                            Evaluate(n+1, P[n+1], R[n][i]));
        sumw = sumw + W[n][i];
        System.out.println("W["+n+"]["+i+"]="+W[n][i]);
      }
      System.out.println("                            sum W="+sumw);
    }
    System.out.println();

    // integrate e^x from a to b
    double a=1.0;
    double b=3.0;
    double exact=StrictMath.exp(b)-StrictMath.exp(a);
    double approx=0.0;
    double err=0.0;

    System.out.println("Gauss-Legendre quadrature of e^x from "+a+
                       " to "+b+" = "+exact);
    for(int n=2; n<N; n++)
    {
      approx=0.0;
      for(int i=0; i<n; i++)
      {
        approx = approx + W[n][i]*StrictMath.exp((a+b+R[n][i]*(b-a))/2.0);
      }
      approx = approx*(b-a)/2.0;
      err = exact-approx;
      System.out.println("n="+n+" approx="+approx+"  err="+err);
    }

    System.out.println();
    PlotPoly();
  }
  
  private static void LRoot(int n, double P[][], double R[][])
  {
    // specialized for roots of Legendre polynomials
    // check first for zero root, reduce into T.
    // T=initial polynomial, store root at R[n][i]
    double T[] = new double[n+1];
    double DT[] = new double[n];
    int i=0;

    if(P[n][0]==0.0)
    { 
      R[n][i]=0.0;
      i++;
      for(int j=1; j<=n; j++) T[j-1] = P[n][j]; // reduce
    }
    else
    {
      for(int j=0; j<=n; j++) T[j] = P[n][j];
    }
    while(i<n)
    {
      for(int j=0; j<n-i; j++)
      {
        DT[j]=(double)(j+1)*T[j+1];
      }
      // do root Newton
      double rold=0.0, r=1.0;
      //System.out.println("r="+r+", y="+Evaluate(n-i, T, r)+
      //                   ", dy="+Evaluate(n-i-1, DT, r));

      for(int k=0; k<20; k++)
      {
        r = r - Evaluate(n-i, T, r)/Evaluate(n-i-1, DT, r);
        if(Math.abs(r-rold)<1.0E-14) break;
        rold = r;
      }
      for(int j=n-i; j>0; j--)
      {
        T[j-1]=T[j-1]+r*T[j];
      }
      for(int j=1; j<=n-i; j++) T[j-1] = T[j]; // reduce
      R[n][i] = r;
      i++;
      r = -r;
      for(int j=n-i; j>0; j--)
      {
        T[j-1]=T[j-1]+r*T[j];
      }
      for(int j=1; j<=n-i; j++) T[j-1] = T[j]; // reduce
      R[n][i] = r;
      i++;
    }
  }
  
  
  private void PlotPoly()
  {
    setTitle("Legendre polynomials");
    setSize(600,400);
    setBackground(Color.white);
    setForeground(Color.black);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    setVisible(true);
  }

  public void paint(Graphics g)
  {
    final int sca=100;
    final int hw=2*sca;
    final int f1xOff=50;
    final int f1yOff=70;
    final int f1xC=f1xOff+hw/2;
    final int f1yC=f1yOff+hw/2;

    final int f2xOff=350;
    final int f2yOff=70;
    final int f2xC=f2xOff+hw/2;
    final int f2yC=f2yOff+hw/2;
    
    g.drawRect(f1xOff, f1yOff, hw, hw);
    g.drawLine(f1xOff-5, f1yC,      f1xOff+hw+5, f1yC);
    g.drawLine(f1xC,   f1yOff-5,    f1xC, f1yOff+hw+5);
    g.drawString("1",  f1xOff-14,   f1yOff+4);
    g.drawString("0",  f1xOff-14,   f1yC+4);
    g.drawString("-1", f1xOff-14,   f1yOff+hw+4);
    g.drawString("-1", f1xOff-4,    f1yOff+hw+15);
    g.drawString("0",  f1xC-4,      f1yOff+hw+15);
    g.drawString("1",  f1xOff+hw-4, f1yOff+hw+15);
    g.drawString("y",  f1xC-4,      f1yOff-12);
    g.drawString("x",  f1xOff+hw+6, f1yC+4);
    
    g.drawRect(f2xOff, f2yOff, hw, hw);
    g.drawLine(f2xOff-5, f2yC,      f2xOff+hw+5, f2yC);
    g.drawLine(f2xC,   f2yOff-5,    f2xC, f2yOff+hw+5);
    g.drawString("5",  f2xOff-14,   f2yOff+4);
    g.drawString("0",  f2xOff-14,   f2yC+4);
    g.drawString("-5", f2xOff-14,   f2yOff+hw+4);
    g.drawString("-1", f2xOff-4,    f2yOff+hw+15);
    g.drawString("0",  f2xC-4,      f2yOff+hw+15);
    g.drawString("1",  f2xOff+hw-4, f2yOff+hw+15);
    g.drawString("y",  f2xC-4,      f2yOff-12);
    g.drawString("x",  f2xOff+hw+6, f2yC+4);

    double xnew, ynew, xold=0.0, yold=0.0;
    Color col[]={Color.yellow, Color.green, Color.red, Color.blue,
                 Color.black, Color.pink, Color.orange};
    double y;
    for(int n=2; n<Math.min(N,6); n++) // up to 8
    {
      g.setColor(col[n-2]);
      for(double x=-1.0; x<=1.0; x=x+1.0/40.0)
      {
        xnew=x;
        ynew=Evaluate(n, P[n], x);
        if(x!=-1.0) // not first time
        {
          g.drawLine((int)(f1xC+(xold/1.0)*sca), (int)(f1yC-(yold/1.0)*sca),
                     (int)(f1xC+(xnew/1.0)*sca), (int)(f1yC-(ynew/1.0)*sca));
        }
        xold=xnew;
        yold=ynew;
      }
    }

    for(int n=2; n<Math.min(N,6); n++) // up to 8
    {
      g.setColor(col[n-2]);
      for(double x=-1.0; x<=1.0; x=x+1.0/40.0)
      {
        xnew=x;
        ynew=Evaluate(n, DP[n], x);
        if(ynew>4.95) ynew=4.95;
        if(ynew<-4.95) ynew=-4.95;
        if(x!=-1.0) // not first time
        {
          g.drawLine((int)(f2xC+(xold/1.0)*sca), (int)(f2yC-(yold/5.0)*sca),
                     (int)(f2xC+(xnew/1.0)*sca), (int)(f2yC-(ynew/5.0)*sca));
        }
        xold=xnew;
        yold=ynew;
      }
    }

    g.setColor(Color.red);
    Font cur16 = new Font("courier", Font.BOLD, 16); 
    g.setFont(cur16);
    g.drawString("y=Pn(x)", 55, 50);
    g.drawString("y=P'n(x)", 355, 50);
    g.setColor(col[0]);
    g.drawString("P2(x) ____", 55, 310);
    g.setColor(col[1]);
    g.drawString("P3(x) ____", 55, 330);
    g.setColor(col[2]);
    g.drawString("P4(x) ____", 55, 350);
    g.setColor(col[3]);
    g.drawString("P5(x) ____", 55, 370);
    g.setColor(col[0]);
    g.drawString("P'2(x) ____", 355, 310);
    g.setColor(col[1]);
    g.drawString("P'3(x) ____", 355, 330);
    g.setColor(col[2]);
    g.drawString("P'4(x) ____", 355, 350);
    g.setColor(col[3]);
    g.drawString("P'5(x) ____", 355, 370);
    g.setColor(Color.black);
    g.drawString("Legendre Polynomials and derivatives", 100, 390);
  }
  
  
  private static double Evaluate(int n, double P[], double x)
  {
    double value=P[n];
    for(int i=n-1; i>=0; i--) value = value*x+P[i];
    return value;
  }
  
  public static void main (String[] args) // demo
  {
    new Legendre(); // construct and execute
  }
}
