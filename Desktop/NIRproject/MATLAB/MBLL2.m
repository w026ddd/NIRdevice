function [hb,hbo] = MBLL2(pa,pb)
t1 = 0.1;
hb = pa;
hbo = pa;
den = (0.335*0.2764 - 0.159*0.191)*2.1;
for i = 1 : length(pb)-1
hb(i,1) = t1;
hbo(i,1)= t1;

   hb(i,2) = ((0.191*(-log(pa(i,2))/6.25)) - (0.159*(-log(pb(i,2))/6.45)))/den ;

    hbo(i,2) = ((0.335*(-log(pb(i,2))/6.45)) - (0.2764*(-log(pa(i,2))/6.25)))/den;
    
t1 = t1 + 0.4;

%6.25
%6.45
end