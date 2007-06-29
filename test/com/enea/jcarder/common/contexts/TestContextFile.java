package com.enea.jcarder.common.contexts;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.enea.jcarder.common.Lock;
import com.enea.jcarder.common.LockingContext;
import com.enea.jcarder.common.contexts.ContextFileReader;
import com.enea.jcarder.common.contexts.ContextFileWriter;
import com.enea.jcarder.util.logging.Logger;


public final class TestContextFile {

    @Test
    public void writeReadTest() throws IOException {
        File file = File.createTempFile(TestContextFile.class.getName(),
                                        null);
        ContextFileWriter writer =
            new ContextFileWriter(new Logger(null), file);
        Lock lock = new Lock("myClassName", 5);
        LockingContext context = new LockingContext("myThreadName",
                                                    "myLockReference",
                                                    "myMethod");
        int lockId = writer.writeLock(lock);
        int contextId = writer.writeContext(context);
        writer.close();
        ContextFileReader reader = new ContextFileReader(new Logger(null), file);
        Assert.assertEquals(lock, reader.readLock(lockId));
        Assert.assertEquals(context, reader.readContext(contextId));
        file.delete();
    }
}
