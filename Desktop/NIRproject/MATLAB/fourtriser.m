%Diogo Dias UID# U00777095
%BME 7110 Biomedical Signals
% Project 2 
%
%
%--- help for fourtriser ---
%
%
%this function allows us to create a fourier trigonometric series for a
%inputted function by infun
% It`s also possible to see each term for each n while it`s trying to get
% the right function.

function fourtriser

%The opt variable is used for controling the while loop to keep the
%program runningis 
opt = 0;

while opt == 0;
    
    %The infun function is called for the user to input his/her desired
    %function
   fprintf('\n Input f(t) /n');
[t1,ft,tat,fat,T,fun] = infunv3; 
    %where and t1, ft are the arrays of time and the f(t) for ploting 
    %the function in a clear way
    %tat and fat are also the time and the function but for the
    %calculations
    %T is the width of the function
    %form is the function form to be integrated on the counts below
    
%the wo of the sinusoids below is calculated below
wo = (2*pi)/T;
%open a window, set its size and name
figure(1);
set(1,'Position',[435 67 629 700]);
set(1,'name','Trigonometric Fourier Series');
%plot of f(t) adjust it`s axis,labels and title
subplot(3,1,1,'align');
plot(t1,ft,'b');

axis([min(t1) max(t1) min(ft) max(ft)]);

xlabel('(t)');
ylabel('f(t)');
title('function');

%simulating the variables N and a that will be used on the integrations below
syms N a;

%calculating a0
%a0 = (1/T)*int((fun),a,min(tat),min(tat))
a0 = (1/T)*sum(fat);
%calcul the energy of the inputted f(t) 
Et = sum(fat.^2);

%calculating an and bn, through the equations below the results of both are
%equations with function of n and t, the t was changed by variable a in order to
%not misconfuse with the time arrays tat and t1, and the period T.
an = (2/T)*int((fun*sin(wo*a*N)),a,min(tat),max(tat));
bn = (2/T)*int((fun*cos(wo*a*N)),a,min(tat),max(tat));

%The variable n is initialized below as well the expression of the 
%function created by the fourier series (c) is initialized  
n =1;
c = a0;

%loop for the construction of the fourier series
while n < 1000
%a fourier term is given by the equation below substituting n for its
%corresponding value of the loop
s =  subs(an,n).*cos(wo*tat*n) + subs(bn,n).*sin(wo*tat*n);
subplot(3,1,2,'align');

plot(tat,s)
xlabel('(t)');
ylabel('s(t)');
title('fourier term');
%The time between counts is given below, this value is small because we can
%have 1000 loops for exponentials functions
pause(0.1)

%The fourier term is added to the fourier series created function
c = c + s;

%The energy of this created function is calculeted below
Ec = sum(c.^2);

%As well the coefficient of correlation between the original signal and the
%created signal through the trigonometric fourier series
Cn = (1/sqrt(Ec*Et))*(sum(c.*fat));

%If the correlation coefficient is close to 1 we can say that the created
%signal has a high correlation to the original signal, and the loop ends
if Cn > 0.98
    n = n+1000;
end
%plot of g(t) the current created by trigonometric fourier series that
%should look to the original function
subplot(3,1,3,'align');

%The function gets too slow for long arrays like the sinusoids and 
%exponential f(t)if we try to reply the ) before the step and
%the ramp so we use the if case below to correct it.
if length(tat)<49
plot( [min(tat)-1 min(tat)-0.01 tat max(tat)+0.01 1] ,[0 0 c 0 0]);
else
    plot(tat,c);
end%end of if case of length
title('fourier series function');
xlabel('(t)');
ylabel('g(t)');

%the step of the n
n = n+1;
end% end of the while of the fourier series construction
opt = 1;
end%end of the opt function
end% end of the function