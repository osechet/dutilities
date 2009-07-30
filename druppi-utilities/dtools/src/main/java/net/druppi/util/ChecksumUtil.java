/*
 * ChecksumUtil.java
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Utility class to calculate checksums.
 *
 * @author Olivier Sechet
 * @version 1.0 - Jul 24, 2009
 */
public final class ChecksumUtil {

    /** The size of the buffer used when reading. */
    private static final int BUFFER_LENGTH = 2048;

    /**
     * Calculate and returns the checksum of the given file.
     *
     * @param file the file to use.
     * @return the file's checksum.
     * @throws IOException if an error occurs when opening or reading the file.
     */
    public static long getCRCChecksum(final File file) throws IOException {
        return getCRCChecksum(file.toURI().toURL());
    }

    /**
     * Calculate and returns the checksum of the given URL.
     *
     * @param url the URL to use.
     * @return the file's checksum.
     * @throws IOException if an error occurs when opening or reading the file.
     */
    public static long getCRCChecksum(final URL url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("The URL cannot be null."); //$NON-NLS-1$
        }
        CheckedInputStream stream = new CheckedInputStream(url.openStream(), new CRC32());

        int len;
        byte[] buffer = new byte[BUFFER_LENGTH];
        do {
            len = stream.read(buffer);
        } while (len >= 0);

        return stream.getChecksum().getValue();
    }

    /**
     * Checks the given checksums against the given URL.
     *
     * @param url the URL to use.
     * @param checksum the checksum to compare.
     * @return true if the checksums are equals.
     * @throws IOException
     * @throws IOException if an error occurs when opening or reading the file.
     */
    public static boolean checkCRCChecksum(final URL url, final long checksum) throws IOException {
        return checksum == getCRCChecksum(url);
    }

    /**
     * Calculate and returns the checksum of the given file.
     *
     * @param file the file to use.
     * @return the file's checksum.
     * @throws IOException if an error occurs when opening or reading the file.
     * @throws NoSuchAlgorithmException if the SHA algorithm cannot be found.
     */
    public static byte[] getSHAChecksum(final File file) throws IOException, NoSuchAlgorithmException {
        return getSHAChecksum(file.toURI().toURL());
    }

    /**
     * Calculate and returns the checksum of the given URL.
     *
     * @param url the URL to use.
     * @return the file's checksum.
     * @throws IOException if an error occurs when opening or reading the url.
     * @throws NoSuchAlgorithmException if the SHA algorithm cannot be found.
     */
    public static byte[] getSHAChecksum(final URL url) throws IOException, NoSuchAlgorithmException {
        if (url == null) {
            throw new IllegalArgumentException("The URL cannot be null."); //$NON-NLS-1$
        }

        MessageDigest msgDigest = MessageDigest.getInstance("SHA"); //$NON-NLS-1$
        BufferedInputStream stream = new BufferedInputStream(url.openStream());
        byte[] buffer = new byte[BUFFER_LENGTH];
        int len = stream.read(buffer);
        while (len >= 0) {
            msgDigest.update(buffer, 0, len);
            len = stream.read(buffer);
        }
        return msgDigest.digest();
    }

    /**
     * Checks the given checksums against the given URL.
     *
     * @param url the url to check.
     * @param digest the checksum to compare.
     * @return true if the checksums are equals.
     * @throws IOException if an error occurs when opening or reading the url.
     * @throws NoSuchAlgorithmException if the SHA algorithm cannot be found.
     */
    public static boolean checkSHAChecksum(final URL url, final byte[] digest) throws NoSuchAlgorithmException, IOException {
        return MessageDigest.isEqual(digest, getSHAChecksum(url));
    }
}
