/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package gov.nasa.jpl.oodt.cas.commons.io;

//JDK imports
import java.io.IOException;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author bfoster
 * @version $Revision$
 * 
 * <p>
 * Describe your class here
 * </p>.
 */
public class LoggerOutputStream extends OutputStream {

    private Logger logger;

    private CharBuffer buffer;

    private Level logLevel;

    public LoggerOutputStream(Logger logger) throws InstantiationException {
        this(logger, Level.INFO);
    }

    public LoggerOutputStream(Logger logger, Level logLevel)
            throws InstantiationException {
        this(logger, 512, logLevel);
    }

    public LoggerOutputStream(Logger logger, int numOfBytesPerWrite)
            throws InstantiationException {
        this(logger, numOfBytesPerWrite, Level.INFO);
    }

    public LoggerOutputStream(Logger logger, int numOfBytesPerWrite,
            Level logLevel) throws InstantiationException {
        this.logger = logger;
        this.buffer = CharBuffer.wrap(new char[numOfBytesPerWrite]);
        this.logLevel = logLevel;
    }

    public void write(int b) throws IOException {
        if (this.buffer.hasRemaining()) {
            this.buffer.put((char) b);
            if (!this.buffer.hasRemaining())
                this.flush();
        } else
            this.logger.log(this.logLevel, ((char) b) + "");
    }

    public void flush() {
        if (this.buffer.position() > 0) {
        	char[] flushContext = new char[this.buffer.position()];
        	System.arraycopy(this.buffer.array(), 0, flushContext, 0, this.buffer.position());
            this.logger.log(this.logLevel, new String(flushContext));
            this.buffer.clear();
        }
    }

}
