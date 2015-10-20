% This function simply build a magnitude spectra of a signal data at a sampling frequency fs
function furie(data,fs)

% fast fourier transform of data
X = fft(data);
N = length(data);
        %building a array of frequencies 
ws = 2*pi/N;
wnorm = -pi:ws:pi;
wnorm = wnorm(1:length(data));
w = wnorm*fs;
        %plotting the spectra of the signal
plot(w,abs(fftshift(X)))

end
