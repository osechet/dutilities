/*
 * ChecksumUtilTest.java
 *
 * Copyright (C) 2009 Olivier Sechet
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.druppi.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 24, 2009
 */
public class ChecksumUtilTest {

    /**
     * Test method for {@link net.druppi.util.ChecksumUtil#getCRCChecksum(java.net.URL)}.
     */
    @Test
    public void testGetCRCChecksumURL() {
        try {
            ChecksumUtil.getCRCChecksum((URL) null);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (final IllegalArgumentException ex) {
            assertTrue(true);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test1"); //$NON-NLS-1$
            long expected = ChecksumUtil.getCRCChecksum(url);
            assertTrue(ChecksumUtil.checkCRCChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test2"); //$NON-NLS-1$
            long expected = ChecksumUtil.getCRCChecksum(url);
            assertTrue(ChecksumUtil.checkCRCChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test3"); //$NON-NLS-1$
            long expected = ChecksumUtil.getCRCChecksum(url);
            assertTrue(ChecksumUtil.checkCRCChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test method for {@link net.druppi.util.ChecksumUtil#getSHAChecksum(java.net.URL)}.
     */
    @Test
    public void testGetSHAChecksumURL() {
        try {
            ChecksumUtil.getSHAChecksum((URL) null);
            fail("Should throw an exception."); //$NON-NLS-1$
        } catch (final IllegalArgumentException ex) {
            assertTrue(true);
        } catch (IOException ex) {
            fail(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test1"); //$NON-NLS-1$
            byte[] expected = ChecksumUtil.getSHAChecksum(url);
            assertTrue(ChecksumUtil.checkSHAChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test2"); //$NON-NLS-1$
            byte[] expected = ChecksumUtil.getSHAChecksum(url);
            assertTrue(ChecksumUtil.checkSHAChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            fail(ex.getMessage());
        }

        try {
            URL url = getClass().getResource("/checksum-test3"); //$NON-NLS-1$
            byte[] expected = ChecksumUtil.getSHAChecksum(url);
            assertTrue(ChecksumUtil.checkSHAChecksum(url, expected));
        } catch (IOException ex) {
            fail(ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            fail(ex.getMessage());
        }
    }
}
