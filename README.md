# codetop "FAQ"

## 3. 无重复字符的最长子串
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), max = 0;
        if(n == 0) {
            return max;
        }
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int[] lens = new int[n];
        lens[0] = 1;
        max = 1;
        charIndexMap.put(s.charAt(0), 0);
        for(int i=1;i<n;i++) {
            char c = s.charAt(i);
            if(!charIndexMap.containsKey(c)) {
                lens[i] = lens[i-1] + 1;
            } else {
                int preSameCharIndex = charIndexMap.get(c);
                if(preSameCharIndex <= i-1-lens[i-1]) {
                    lens[i] = lens[i-1] + 1;
                } else {
                    lens[i] = i - preSameCharIndex;
                }
            }
            charIndexMap.put(c, i);
            max = Math.max(max, lens[i]);
        }
        return max;
    }
}
```

## 206. 反转链表
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while(head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }
}
```

## 146. LRU缓存机制
```java
class LRUCache {

    private static class DLNode {
        private int key;
        private int value;
        private DLNode prev;
        private DLNode next;

        public DLNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public DLNode getPrev() {
            return prev;
        }

        public void setPrev(DLNode prev) {
            this.prev = prev;
        }

        public DLNode getNext() {
            return next;
        }

        public void setNext(DLNode next) {
            this.next = next;
        }
    }
    
    private int capacity;
    private int size;
    private DLNode head;
    private DLNode tail;
    private Map<Integer, DLNode> nodeMap;
    public LRUCache(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("the capacity must greater than 0");
        }
        this.capacity = capacity;
        this.size = 0;
        this.head = new DLNode(-1, -1);
        this.tail = new DLNode(-1, -1);
        this.head.next = tail;
        this.tail.prev = head;
        this.nodeMap = new HashMap<>();
    }

    // get
    // 查map 无返回 有放队头返回
    public int get(int key) {
        if(!nodeMap.containsKey(key)) {
            return -1;
        } else {
            DLNode targetNode = nodeMap.get(key);
            removeNode(targetNode);
            addHead(targetNode);
            return targetNode.getValue();
        }
    }

    // put
    // 查map是否存在
    // 存在则更新value，删除并放到队头
    // 不存在则新增节点，并放入map，检查队列容量，大于capacity则删除一个队尾元素并从map删除
    public void put(int key, int value) {
        if(nodeMap.containsKey(key)) {
            DLNode targetNode = nodeMap.get(key);
            removeNode(targetNode);
            targetNode.setValue(value);
            addHead(targetNode);
        } else {
            DLNode newNode = new DLNode(key, value);
            addHead(newNode);
            nodeMap.put(key, newNode);
            size++;
            if(size > capacity) {
                DLNode tailNode = removeTail();
                nodeMap.remove(tailNode.getKey());
                size--;
            }
        }
    }
    
    private void addHead(DLNode node) {
        DLNode headNext = head.next;
        head.next = node;
        node.prev = head;
        node.next = headNext;
        headNext.prev = node;
    }
    private DLNode removeTail() {
        DLNode tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }
    private void removeNode(DLNode node) {
        DLNode prev = node.prev;
        DLNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }
}
```

## 215. 数组中的第K个最大元素
```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int lo = 0, hi = nums.length - 1;
        while(lo <= hi) {
            int p = partition(nums, lo, hi);
            if(p+1 == k) {
                return nums[p];
            } else if(p+1 < k) {
                lo = p + 1;
            } else {
                hi = p - 1;
            }
        }
        return -1;
    }
    private int partition(int[] nums, int lo, int hi) {
        if(lo == hi) {
            return lo;
        }
        int i = lo, j = hi + 1;
        while(true) {
            while(nums[++i]>nums[lo]) {
                if(i == hi) {
                    break;
                }
            }
            while(nums[--j]<nums[lo]) {
                if(j == lo) {
                    break;
                }
            }
            if(i >= j) {
                break;
            }
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        int tmp = nums[lo];
        nums[lo] = nums[j];
        nums[j] = tmp;
        return j;
    }
}
```

## 25. K 个一组翻转链表
```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        int nodeCount = 0;
        ListNode cur = head;
        for(int i=0;i<k;i++) {
            if(cur == null) {
                break;
            }
            nodeCount++;
            cur = cur.next;
        }
        if(nodeCount < k) {
            return head;
        } else {
            ListNode last = cur;
            ListNode newHead = null;
            cur = head;
            while(cur != last) {
                ListNode tmpNext = cur.next;
                cur.next = newHead;
                newHead = cur;
                cur = tmpNext;
            }
            head.next = reverseKGroup(last, k);
            return newHead;
        }
    }
}
```

## 15. 三数之和
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<nums.length-2;i++) {
            if(i>0 && nums[i]==nums[i-1]) {
                continue;
            } else {
                List<List<Integer>> twoSums = twoSum(nums, i+1, nums.length-1, -nums[i]);
                for(List<Integer> ts : twoSums) {
                    List<Integer> item = new ArrayList<>(ts);
                    item.add(nums[i]);
                    res.add(item);
                }
            }
        }
        return res;
    }
    private List<List<Integer>> twoSum(int[] nums, int startIndex, int endIndex, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int lo = startIndex, hi = endIndex;
        while(lo < hi) {
            if(lo>startIndex && nums[lo]==nums[lo-1]) {
                lo++;
                continue;
            }
            if(hi<endIndex && nums[hi]==nums[hi+1]) {
                hi--;
                continue;
            }
            if(nums[lo]+nums[hi] == target) {
                List<Integer> item = new ArrayList<>();
                item.add(nums[lo]);
                item.add(nums[hi]);
                res.add(item);
                lo++;
                hi--;
            } else if(nums[lo]+nums[hi] < target) {
                lo++;
            } else {
                hi--;
            }
        }
        return res;
    }
}
```

## 53. 最大子数组和
```java
class Solution {
    public int maxSubArray(int[] nums) {
        int max = nums[0], prev = nums[0];
        for(int i=1;i<nums.length;i++) {
            prev = Math.max(nums[i], prev+nums[i]);
            max = Math.max(max, prev);
        }
        return max;
    }
}
```

## 
```java

```