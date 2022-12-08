public class FileParsingException extends Exception{
    public FileParsingException(String message, String fileName, int lineNumber)
    {
        super(message);
        StackTraceElement[] prev = this.getStackTrace();
        StackTraceElement[] newStack = new StackTraceElement[prev.length + 1];
        StackTraceElement top = new StackTraceElement(
            fileName,
            "",
            fileName,
            lineNumber
        );
        newStack[0] = top;

        int i = 1;
        for (StackTraceElement element : prev)
        {
            newStack[i] = new StackTraceElement(
                new String(element.getClassName()),
                new String(element.getMethodName()),
                new String(element.getFileName()),
                element.getLineNumber()
            );
            i++;
        }
        this.setStackTrace(newStack);
    }
}
