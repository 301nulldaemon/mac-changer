# adds the module that lets you execute to bash through the python script.
import subprocess

# module that adds options and arguments, so instead of having the user input their mac and interface through individual inputs like before, it imports the module to allows them to use arguments such as "-i", "-m", and so on. (more args coming soon!)
import optparse

# a module that adds regex parsing.
import re


# Function to validate the MAC address
def validate_mac_address(mac):
    # Regular expression to match a MAC address (6 bytes, each byte 2 hexadecimal digits, separated by colons)
    mac_pattern = r'^([0-9a-fA-F]{2}[:.-]){5}[0-9a-fA-F]{2}$'
    
    # Check if the MAC address matches the pattern
    if not re.match(mac_pattern, mac):
        return False  # Invalid MAC address
    return True  # Valid MAC address


# creates a function that implements arguments into the program, ie. "python mac.py -i wlan0 -m 00:11:22:33:44:55", lets -i be used for interface, and -m for mac address.
def getargs():
    # this little piece of code adds an object that can parse arguments, ie. the little "-i" things and whatnot.
    parser = optparse.OptionParser()
    # these 2 pieces of code call the parser object, and then calls the method add_option, which adds the option to use shortened args (or full length if you're weird...), and then puts those into the variables address, and interface, respectively.
    parser.add_option("-i", "--interface", dest="interface", help="Interface to change the mac address.")
    parser.add_option("-m", "--mac", dest="address", help="MAC address to change to.")
    # this now returns the results of parser.parse_args to anything that calls this getargs function.
    (options, arguments) = parser.parse_args()
    # displays an error and usage message if the user doesnt input one or the other
    if not options.interface:
        print("Error: Interface must be provided.\n Usage: python mac.py -i <interface> -m <mac_address>")
        parser.error("no interface")
    elif not options.address:
        print("Error: MAC address must also be provided.\n Usage: python mac.py -i <interface> -m <mac_address>")
        parser.error("no mac")
    
    # Validate MAC address format
    if not validate_mac_address(options.address):
        print("Error: Invalid MAC address format. Please provide a valid MAC address (e.g., 00:11:22:33:44:55).")
        parser.error("invalid MAC address")
    
    return options


# creates a function that contains the "heart" of this program; the system commands used to actually change the mac address, for ease of future access.
def changemac(intr, adr):
    try:
        # prints that the MAC address is, indeed, about to change.
        print('✅ Changing MAC address for ' + intr + " to " + adr + ".\n")
        # runs the basic shell commands that takes down the interface given, changes the address to whatever you set it to, and then puts it back up.
        subprocess.call(["sudo", "ip", "link", "set", "dev", intr, "down"],)
        subprocess.call(["sudo", "ip", "link", "set", "dev", intr, "address", adr])
        subprocess.call(["sudo", "ip", "link", "set", "dev", intr, "up"])
    except subprocess.CalledProcessError as e:
        # Handle errors that arise from subprocess execution.
        print("🚨 Error while changing MAC address:")
        print(e)


# creates a function that gets the current mac address, of the provided interface.
def getcurrentmac(intr):
    # stores all the info of the given interface, like mac address, ip address, etc, in the "confirm" variable
    confirm = subprocess.check_output(["ip", "link", "show", "dev", intr])

    # decodes any byte-objects within the "confirm" output, rendering it printable
    confirm_decoded = confirm.decode("utf-8")
    # uses regex to search through the output of confirm_decoded, specifically only for the mac address.
    macsearch = re.search(r"ether ([0-9a-f:]{17})", confirm_decoded)
    # if it was able to find the mac address, it returns the mac address to the line it will be called to, but returns group 1, which omits the "ether" string from appearing, for a cleaner look. if it doesnt find a mac address, it says not found.
    if macsearch:
        return macsearch.group(1)
    else:
        print("💔 MAC address not found...")


# calls the getargs function, and funnels the result into options which renders it usable for the variables below.
options = getargs()

# Retrieve and display the current MAC address before making changes.
currentmac = getcurrentmac(options.interface)
print("🔷 Current MAC address is: " + str(currentmac) + "\n")

# variables for the interface and address arguments, for future use.
intr = options.interface
adr = options.address

# just calls the block of code that actually changes the mac, and accepts interface and mac as inputs.
changemac(intr, adr)


# gets the current address again, and confirms that it has been changed. or not if the condition wasn't met.
currentmac = getcurrentmac(options.interface)
if currentmac == adr:
    print("💖 MAC address changed! Confirmation below.\n")
    print("🌟 Now your current MAC address is: " + adr + "!")
else:
    print("❌ MAC address was unable to be changed.")

     
