
Example
----

Generate the [miniaudio](https://github.com/mackron/miniaudio) bindings

```shell
java src/Main.java \
'--header=/path/to/miniaudio.h' \
'--args=-I;/usr/include' \
'--enable-macro=true' \
'--out=miniaudio-out/src' \
'--pkg-name=libminiaudio' \
'--name=MiniAudio' \
'--filter-str=miniaudio.h'
```

Using miniaudio to play the music file

```java
import libminiaudio.MiniAudioFunctionSymbols;
import libminiaudio.MiniAudioSymbolProvider;
import libminiaudio.aggregates.ma_engine;
import libminiaudio.aggregates.ma_engine_config;
import libminiaudio.aggregates.ma_log;
import libminiaudio.common.FunctionUtils;
import libminiaudio.common.PtrI;
import libminiaudio.common.Str;
import libminiaudio.enumerates.ma_result;
import libminiaudio.functions.ma_log_callback_proc;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.util.Optional;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Set the symbol provider for MiniAudio.
        MiniAudioSymbolProvider.symbolProvider = new FunctionUtils.SymbolProvider() {
            // Override the provide method to look up symbols from the miniaudio shared library.
            @Override
            public Optional<FunctionUtils.Symbol> provide(String name) {
                // Find the symbol in the miniaudio.so library using global arena and throw an exception if not found.
                return Optional.of(new FunctionUtils.FunctionSymbol(SymbolLookup.libraryLookup("/path/to/miniaudio.so", Arena.global()).find(name).orElseThrow()));
            }
        };

        // Create a confined arena for memory management.
        Arena mem = Arena.ofConfined();
        // Initialize the engine configuration.
        ma_engine_config ma_engine_config = MiniAudioFunctionSymbols.ma_engine_config_init(mem);
        // Create a log object.
        ma_log log = new ma_log(mem);
        // Initialize the log with no user data.
        MiniAudioFunctionSymbols.ma_log_init(PtrI.of(MemorySegment.NULL), log.operator().getPointer());
        // Register a callback function for the log to print messages to the console.
        MiniAudioFunctionSymbols.ma_log_register_callback(log.operator().getPointer(), MiniAudioFunctionSymbols.ma_log_callback_init(mem, new ma_log_callback_proc(mem,
                // Define the callback function that prints the log level and message.
                (ma_log_callback_proc.Function) (pUserData, level, pMessage)
                        -> System.out.print(new Str(MiniAudioFunctionSymbols.ma_log_level_to_string(level)) + " : " + new Str(pMessage).get())), PtrI.of(MemorySegment.NULL)));
        // Assign log to the engine configuration.
        ma_engine_config.pLog(log.operator().getPointer());

        // Create a new engine instance.
        ma_engine engine = new ma_engine(mem);
        // Initialize the audio engine with the given configuration and engine object.
        ma_result result = MiniAudioFunctionSymbols.ma_engine_init(ma_engine_config.operator().getPointer(), engine.operator().getPointer());
        // Check if the engine initialization was successful.
        // If it failed, print an error message and exit.
        if (!ma_result.MA_SUCCESS.equals(result)) {
            System.out.println("Failed");
            return;
        }

        // Play the specified sound file.
        MiniAudioFunctionSymbols.ma_engine_play_sound(engine.operator().getPointer(), new Str(mem, "music.flac"), PtrI.of(MemorySegment.NULL));
        // Wait for user input to stop the sound.
        new Scanner(System.in).nextLine();
        // Uninitialize the engine.
        MiniAudioFunctionSymbols.ma_engine_uninit(engine.operator().getPointer());
    }
}
```

Generate the Vulkan and [VMA](https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator) bindings

```shell
java src/Main.java \
'--header=/usr/include/vulkan/vulkan.h' \
'--args=-I;/usr/include' \
'--enable-macro=true' \
'--out=vulkan-out/src' \
'--pkg-name=vk' \
'--name=Vulkan' \
'--filter-str=vulkan/vulkan.h' \
'--header=/usr/include/vk_mem_alloc.h' \
'--args=-I;/usr/include' \
'--enable-macro=true' \
'--out=vma-out/src' \
'--pkg-name=vma' \
'--name=Vma' \
'--filter-str=vk_mem_alloc.h'
```

Build libvma.so

```shell
echo '#define VMA_IMPLEMENTATION

#if defined(_WIN32)
#define VMA_CALL_PRE extern __declspec(dllexport)
#else
#define VMA_CALL_PRE extern __attribute__((visibility("default")))
#endif

#include <vulkan/vulkan.h>
#include <cstdio>
#include <vk_mem_alloc.h>' > vma.cpp
g++ vma.cpp -fPIC -shared -DVMA_STATIC_VULKAN_FUNCTIONS=0 -DVMA_DYNAMIC_VULKAN_FUNCTIONS=1 -o libvma.so
```

Using VMA to allocate 1024 MiB GPU memory

```java
import static vk.VulkanFunctionSymbols.vkCreateDevice;
import static vk.VulkanFunctionSymbols.vkCreateInstance;
import static vk.VulkanFunctionSymbols.vkEnumeratePhysicalDevices;
import static vk.VulkanMacros.VK_API_VERSION_1_0;
import static vk.enumerates.VkBufferUsageFlagBits.VK_BUFFER_USAGE_TRANSFER_DST_BIT;
import static vk.enumerates.VkResult.VK_SUCCESS;
import static vk.enumerates.VkSharingMode.VK_SHARING_MODE_EXCLUSIVE;
import static vk.enumerates.VkStructureType.VK_STRUCTURE_TYPE_APPLICATION_INFO;
import static vk.enumerates.VkStructureType.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;
import static vk.enumerates.VkStructureType.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
import static vk.enumerates.VkStructureType.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
import static vma.VmaFunctionSymbols.vmaCreateAllocator;
import static vma.VmaFunctionSymbols.vmaCreateBuffer;
import static vma.enumerates.VmaMemoryUsage.VMA_MEMORY_USAGE_CPU_TO_GPU;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.util.Optional;
import java.util.Scanner;

import vk.VulkanSymbolProvider;
import vk.aggregates.VkApplicationInfo;
import vk.aggregates.VkBufferCreateInfo;
import vk.aggregates.VkDeviceCreateInfo;
import vk.aggregates.VkInstanceCreateInfo;
import vk.common.Array;
import vk.common.FunctionUtils;
import vk.common.I32I;
import vk.common.I64I;
import vk.common.PtrI;
import vk.common.Str;
import vk.functions.PFN_vkGetDeviceProcAddr;
import vk.functions.PFN_vkGetInstanceProcAddr;
import vk.values.VkBuffer;
import vk.values.VkDevice;
import vk.values.VkInstance;
import vk.values.VkPhysicalDevice;
import vk.values.uint32_t;
import vma.VmaSymbolProvider;
import vma.aggregates.VmaAllocationCreateInfo;
import vma.aggregates.VmaAllocatorCreateInfo;
import vma.aggregates.VmaVulkanFunctions;
import vma.values.VmaAllocation;
import vma.values.VmaAllocator;


public class Main {
    public static void main(String[] args) {
        VulkanSymbolProvider.symbolProvider = new FunctionUtils.SymbolProvider() {
            @Override
            public Optional<FunctionUtils.Symbol> provide(String name) {
                return Optional.of(new FunctionUtils.FunctionSymbol(SymbolLookup.libraryLookup("libvulkan.so", Arena.global()).find(name).orElseThrow()));
            }
        };
        VmaSymbolProvider.symbolProvider = new FunctionUtils.SymbolProvider() {
            @Override
            public Optional<FunctionUtils.Symbol> provide(String name) {
                return Optional.of(new FunctionUtils.FunctionSymbol(SymbolLookup.libraryLookup("/path/to/libvma.so", Arena.global()).find(name).orElseThrow()));
            }
        };

        Arena mem = Arena.ofConfined();
        Array<VkInstance> instances = VkInstance.list(mem, 1);
        Array<VkApplicationInfo> appInfos = VkApplicationInfo.list(mem, 1);
        VkApplicationInfo appInfo = appInfos.getFirst();
        appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
        appInfo.pApplicationName(Str.list(mem, new String[]{"Vulkan Memory Allocation Example"}).getFirst());
        appInfo.applicationVersion(I32I.of(1));
        appInfo.pEngineName(Str.list(mem, new String[]{"No Engine"}).getFirst());
        appInfo.engineVersion(I32I.of(1));
        appInfo.apiVersion(I32I.of(VK_API_VERSION_1_0));

        Array<VkInstanceCreateInfo> createInfos = VkInstanceCreateInfo.list(mem, 1);
        VkInstanceCreateInfo createInfo = createInfos.getFirst();
        createInfo.sType(I32I.of(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO));
        createInfo.pApplicationInfo(appInfos);

        if (!vkCreateInstance(createInfos, PtrI.of(MemorySegment.NULL), instances).equals(VK_SUCCESS)) {
            System.err.println("Failed to create Vulkan instance!");
            return;
        }

        VkInstance instance = instances.getFirst();

        // Create a Vulkan device
        Array<vk.values.VkDevice> devices = vk.values.VkDevice.list(mem, 1);

        // Enumerate physical devices and pick the first one
        Array<uint32_t> deviceCount = uint32_t.list(mem, 1);
        vkEnumeratePhysicalDevices(instance, deviceCount, PtrI.of(MemorySegment.NULL));

        if (deviceCount.getFirst().operator().value() == 0) {
            System.err.println("Failed to find GPUs with Vulkan support!");
            return;
        }

        Array<VkPhysicalDevice> devicesList = VkPhysicalDevice.list(mem, deviceCount.size());
        vkEnumeratePhysicalDevices(instance, deviceCount, devicesList);
        VkPhysicalDevice physicalDevice = devicesList.getFirst(); // Take the first available device

        // Create the logical device
        Array<VkDeviceCreateInfo> deviceCreateInfos = VkDeviceCreateInfo.list(mem, 1);
        VkDeviceCreateInfo deviceCreateInfo = deviceCreateInfos.getFirst();
        deviceCreateInfo.sType(I32I.of(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO));

        if (!vkCreateDevice(physicalDevice, deviceCreateInfos, PtrI.of(MemorySegment.NULL), devices).equals(VK_SUCCESS)) {
            System.err.println("Failed to create Vulkan device!");
            return;
        }
        VkDevice device = devices.getFirst();

        Array<VkBuffer> buffer = VkBuffer.list(mem, 1);

        VmaVulkanFunctions vmaVkFuncs = new VmaVulkanFunctions(mem)
                .vkGetInstanceProcAddr(new PFN_vkGetInstanceProcAddr(VulkanSymbolProvider.symbolProvider.provide("vkGetInstanceProcAddr").orElseThrow().getSymbol()));
        vmaVkFuncs.vkGetDeviceProcAddr(new PFN_vkGetDeviceProcAddr(VulkanSymbolProvider.symbolProvider.provide("vkGetDeviceProcAddr").orElseThrow().getSymbol()));


        VmaAllocatorCreateInfo allocatorInfo = VmaAllocatorCreateInfo.list(mem, 1).getFirst();
        allocatorInfo.physicalDevice(physicalDevice);
        allocatorInfo.device(device);
        allocatorInfo.instance(instance);
        allocatorInfo.pVulkanFunctions(vmaVkFuncs.operator().getPointer());
        Array<VmaAllocator> allocator = VmaAllocator.list(mem, 1);
        if (!vmaCreateAllocator(allocatorInfo.operator().getPointer(), allocator).equals(VK_SUCCESS)) {
            System.out.println("Failed to create VMA allocator!");
        }
        VmaAllocator allocatorValue = allocator.getFirst();
        Array<VmaAllocation> allocation = VmaAllocation.list(mem, 1);
        createBuffer(allocatorValue, 1024 * 1024 * 1024, VK_BUFFER_USAGE_TRANSFER_DST_BIT.operator().value(), buffer, allocation);
        System.out.println("Allocated 1024 MiB GPU memory");
        System.out.println("Press enter to exit...");
        new Scanner(System.in).nextLine();
    }


    static void createBuffer(VmaAllocator allocator, long size, int usage,
                             Array<VkBuffer> buffer, Array<VmaAllocation> allocation) {
        Arena mem = Arena.ofConfined();
        Array<VkBufferCreateInfo> bufferInfo = VkBufferCreateInfo.list(mem, 1);
        bufferInfo.getFirst().sType(I32I.of(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO));
        bufferInfo.getFirst().size(I64I.of(size));
        bufferInfo.getFirst().usage(I32I.of(usage));
        bufferInfo.getFirst().sharingMode(I32I.of(VK_SHARING_MODE_EXCLUSIVE));
        Array<VmaAllocationCreateInfo> allocInfo = VmaAllocationCreateInfo.list(mem, 1);
        allocInfo.getFirst().usage(VMA_MEMORY_USAGE_CPU_TO_GPU);


        if (!vmaCreateBuffer(allocator, bufferInfo.getFirst().operator().getPointer(), allocInfo,
                buffer, allocation, PtrI.of(MemorySegment.NULL)).equals(VK_SUCCESS)) {
            System.err.println("Failed to create buffer with VMA!");
        }
        mem.close();
    }
}
```
