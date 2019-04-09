// FejerFit.java
//
// approximate f(x) ~ a0 + a1 cos(x) + b1 sin(x) +
//                         (k-1)/k a2 cos(2x) + (k-1)/k b2 sin(2x) +...+ 
//                         1/k ak cos(kx) + 1/k bk sin(kx)
// ai = 2/2pi int 0 to 2pi f(x) cos(i x) dx
// bi = 2/2pi int 0 to 2pi f(x) sin(i x) dx
// a0 = a0/2

package myjava;

import java.awt.*;
import java.awt.event.*;
import java.text.*;

class FejerFit extends Frame
{
  FejerFit()
  {
    setTitle("FejerFit(x) 0 to 2pi of step function");
    setSize(450,500);
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

  double f(double x) // simple step function 0 to 2pi
                     // poor for odd number of points
  {
    if(x<Math.PI) return -0.5;
    return 0.5;
  }

  public void paint(Graphics g)
  {
    final double tpi=2.0*Math.PI;

    // set up function to be approximated
    final int n=100;            // number of samples
    final double nf=(double)n;
    double fx[] = new double[n]; // f(x)
    double ax[] = new double[n]; // approximation(x)
    final double xmin=0.0;
    final double xmax=tpi;
    double ymin;                 // for automatic scaling some day
    double ymax;
    double dx;                   // x step increment
    double xstart;               // initial value
    if(n%2==0) // even, center samples, not end points or center
    {
      dx=(xmax-xmin)/nf;
      xstart=xmin+dx/2.0;
    } 
    else // odd, end points are samples, center point in center
    {
      dx=(xmax-xmin)/(double)(n-1);
      xstart=xmin;
    }
    // do NOT use x=x+dx, very inaccurate!
    for(int i=0; i<n; i++) fx[i]=f(xstart+(double)i*dx);
    ymin=fx[0];
    ymax=fx[0];
    for(int i=0; i<n; i++)
    {
      ymin=Math.min(ymin,fx[i]);
      ymax=Math.max(ymax,fx[i]);
    }
    // may fudge ymin and ymax for extra room for approximation
    
    // set up plot area
    final int hw=400;   // graph area height and width
    final double xsca=hw/(xmax-xmin);  // x data scale 0 to tpi
    final double ysca=hw/2.0;  // y data scale -1 to +1
    final int f1xOff=25;  // offsets
    final int f1yOff=70;
    final int f1xC=f1xOff+hw/2; // centers
    final int f1yC=f1yOff+hw/2;
    
    g.drawRect(f1xOff, f1yOff, hw, hw);
    g.drawLine(f1xOff, f1yOff+hw/2, f1xOff+hw, f1yOff+hw/2);
    g.drawLine(f1xC,   f1yOff, f1xC, f1yOff+hw);
    g.drawString("1",  f1xOff-14,   f1yOff+4);
    g.drawString("0",  f1xOff-14,   f1yOff+hw/2+4);
    g.drawString("-1",  f1xOff-14,   f1yOff+hw+4);
    g.drawString("0",  f1xOff-4,    f1yOff+hw+15);
    g.drawString("pi",  f1xC-4,      f1yOff+hw+15);
    g.drawString("2pi", f1xOff+hw-4, f1yOff+hw+15);
    g.drawString("y",  f1xOff,      f1yOff-12);
    g.drawString("x",  f1xOff+hw+6, f1yOff+hw+4);

    // plot function
    double x1new, y1new;
    double x1old=xstart;
    double y1old=fx[0];
    g.setColor(Color.black);
    for(int i=1; i<n; i++) // n-1 lines
    {
      x1new=xstart+(double)i*dx;
      y1new=fx[i];
      g.drawLine((int)(f1xOff+x1old*xsca), (int)(f1yC-y1old*ysca),
                 (int)(f1xOff+x1new*xsca), (int)(f1yC-y1new*ysca));
      x1old=x1new;
      y1old=y1new;
    }
    
    // compute approximation
    final int nt=n/2;
    double a[] = new double[nt]; // coefficients
    double b[] = new double[nt];
    // compute Fejer series coefficients a's and b's
    for(int i=0; i<nt; i++)
    {
      a[i]=0.0;
      b[i]=0.0;
      for(int j=0; j<n; j++) // n samples
      {
        a[i]=a[i]+fx[j]*Math.cos((double)i*(double)j*tpi/nf);
        b[i]=b[i]+fx[j]*Math.sin((double)i*(double)j*tpi/nf);
      }
      a[i]=2.0*a[i]/nf; // scale numerical integration
      b[i]=2.0*b[i]/nf;
      System.out.println("a["+i+"]="+a[i]+"  b["+i+"]="+b[i]);
    }
    
    Color col[]={Color.yellow, Color.green, Color.red, Color.blue};
    int t=0;                     // terms actually used
    double avgerr, maxerr, rmserr;
    DecimalFormat f1 = new DecimalFormat("0.000");
        
    for(int k=0; k<4; k++)
    {
      // generate the k_th approximation
      t=2*k+4; // only odd harmonics
      avgerr=0.0;
      maxerr=0.0;
      rmserr=0.0;
      for(int i=0; i<n; i++) // n samples
      {
        x1new=xstart+(double)i*dx;
        y1new=a[0]+b[0]+a[1]*Math.cos(x1new)+b[1]*Math.sin(x1new);
        for(int j=3; j<t; j=j+2)y1new=y1new+
                   ((double)(t-j)/(double)(t-1))*a[j]*Math.cos((double)j*x1new)+
                   ((double)(t-j)/(double)(t-1))*b[j]*Math.sin((double)j*x1new);
        ax[i]=y1new;
        avgerr=avgerr+Math.abs(fx[i]-ax[i]);
        maxerr=Math.max(maxerr, Math.abs(fx[i]-ax[i]));
        rmserr=rmserr+(fx[i]-ax[i])*(fx[i]-ax[i]);
      }
        avgerr=avgerr/nf;
        rmserr=Math.sqrt(rmserr/nf);
        System.out.println("for "+(t-1)+" terms: avgerr="+avgerr);
        System.out.println("            maxerr="+maxerr);
        System.out.println("            rmserr="+rmserr);

      // plot approximation
      g.setColor(col[k]);
      x1old=xstart;
      y1old=ax[0];
      for(int i=1; i<n; i++) // n-1 lines
      {
        x1new=xstart+(double)i*dx;
        y1new=ax[i];
        g.drawLine((int)(f1xOff+x1old*xsca), (int)(f1yC-y1old*ysca),
                   (int)(f1xOff+x1new*xsca), (int)(f1yC-y1new*ysca));
        x1old=x1new;
        y1old=y1new;
      }
      g.drawString((t-1)+" terms,  avg error = "+f1.format(avgerr), f1xOff+20, f1yOff+hw-10-15*k); 
    }
    g.setColor(Color.black);
    g.drawString(n+" samples", f1xC+20, f1yOff+hw-20);
    g.setColor(Color.red);
    Font cur18 = new Font("courier", Font.BOLD, 18); 
    g.setFont(cur18);
    g.drawString("FejerFit(x) 0 to 2pi of step function", 10, 50);
  }
  
  public static void main(String args[])
  {
    System.out.println("FejerFit.java running");
    new FejerFit();
  }
}
