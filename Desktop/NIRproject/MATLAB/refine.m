function [px] = refine(py)

px = py;

m = mean(py(1:end,2));
s = std(py(1:end,2));

for i = 1:length(py)
   if(py(i,2) > m)
   px(i,2) = py(i,2) - s;    
   else
   px(i,2) = py(i,2) + s;    
   end
    
end
end