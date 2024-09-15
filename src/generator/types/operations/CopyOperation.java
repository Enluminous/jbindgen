package generator.types.operations;

public interface CopyOperation {

    /**
     * return the string that construct the type
     */
    String copyFromMS(String ms, long offset);

    /**
     *return the string that copy the type to memory segment
     */
    String copyToMS(String ms, long offset, String varName);
}
