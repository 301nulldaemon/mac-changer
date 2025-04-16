## README
This project was made as a personal challenge to implement what i've learned within my first CS Principles class. This project uses Java Swing, which is the GUI toolkit used to make the GUI with backend python integration. 

⚠️ Currently only supports linux. I _may_ add windows compatibility at some point, but due to OS-level limitations, this is not guaranteed.


## GUI USAGE
```
git clone https://github.com/301nulldaemon/mac-changer.git

cd mac-changer/code

./program.sh
```

## CLI USAGE
```
git clone https://github.com/301nulldaemon/mac-changer.git

cd mac-changer/code

python mac.py -i [interface] -m [mac_address]

**For the MAC, make sure it is _6 bytes long,_ and starts with 00.**

```
### EXAMPLE CLI USAGE
```
python mac.py -i wlan0 -m 00:99:23:27:95:32
```


