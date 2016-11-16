package com.example.travissimple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by raul on 16/11/2016.
 */
public class EmailUnitTest {

    @Test
    public void obfuscatesShortEmails() throws Exception {
        EmailObfuscator emailObfuscator = new EmailObfuscator();
        assertEquals(emailObfuscator.obfuscate("a@gmail.com"),   "a***@gmail.com");
        assertEquals(emailObfuscator.obfuscate("ab@gmail.com"),  "a***@gmail.com");
        assertEquals(emailObfuscator.obfuscate("abc@gmail.com"), "a***@gmail.com");

        assertNotEquals(emailObfuscator.obfuscate("a@gmail.com"), "a@gmail.com");
    }

    @Test
    public void obfuscatesLongEmails() throws Exception {
        EmailObfuscator emailObfuscator = new EmailObfuscator();
        assertEquals(emailObfuscator.obfuscate("abcd@gmail.com"), "a***d@gmail.com");
        assertEquals(emailObfuscator.obfuscate("abcdefg@yahoo.com"), "a***g@yahoo.com");
        assertEquals(emailObfuscator.obfuscate("absdiofjidofjiodsfjsfcdefg@yahoo.com"), "a***g@yahoo.com");
    }

}