
[Readme Unfinished Yet]

This project implemented multiple ciphers that can encrypt or decrypt a message or a given file.

The ciphers include: 
* Monosubstitution cipher, 
* Caesar cipher, 
* Random substitution cipher, 
* Vigenere cipher, 
* RSA cipher.

The project can be run with command line. The command will be the following form:
java -jar myCiphers.jar <cipher_type> <cipher_function> <output_options>

The <cipher_type> can be one of the following:
* `--monosub <file>`, 
* `--caesar <file>`, 
* `--random`, 
* `--crackedCaesar [-t <examples> | -c <encrypted>]`, 
* `--vigenere <key>`, 
* `--vigenereL <file>`, 
* `--rsa`, 
* `--rsaPr <file>`, 
* `--rsaPu <file>`.
  
The <cipher_function> can be one of the following:
* `--em <message>`, 
* `--ef <file>`, 
* `--dm <message>`, 
* `--df <file>`.
 
The <output_options> can be one or multiple of the following:
* `--print`, 
* `--out <file>`, 
* `--save <file>`, 
* `--savePu <file>`.
