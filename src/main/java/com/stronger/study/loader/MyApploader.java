package com.stronger.study.loader;

import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class MyApploader extends ClassLoader {



    public MyApploader(ClassLoader parent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        super(parent);
    }

    @SneakyThrows
    protected Class<?> loadClass(String name, boolean resolve) {
        return loadClass(name, resolve,true);
    }

    @SneakyThrows
    protected Class<?> loadClass(String name, boolean resolve, boolean enhance) {
        Class<?> c = findLoadedClass(name);
        //双亲委派
        if (c != null) {
            return c;
        }

        if (isBlackListClass(name)) c = this.getParent().loadClass(name);

        if (c == null) if (enhance) c = loadAppClass(name);

        if (null == c) {
            return super.loadClass(name, resolve);
        } else {
            if (resolve) this.resolveClass(c);
            return c;
        }
    }

    private Class<?> loadAppClass(String name) throws IOException {
        val in = this.getParent().getResourceAsStream(name.replace(".", "/") + ".class");
        var bytes = new byte[in.available()];
        in.read(bytes);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public static boolean isBlackListClass(String name) {
        return name.startsWith("java.")
                || name.startsWith("javax.")
                || name.startsWith("com.google.")
                || name.startsWith("org.apache.")
                || name.startsWith("sun.")
                || name.startsWith("com.sun.")
                || name.startsWith("com.IceCreamQAQ.Yu.hook")
                || name.startsWith("com.IceCreamQAQ.Yu.loader.enchant")
                ;
    }
}

