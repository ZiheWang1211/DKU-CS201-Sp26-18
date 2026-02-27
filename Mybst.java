import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

    class MyBST {
        private class BSTNode {
            ArrayList<PeopleRecord> records;
            BSTNode left, right;
            BSTNode(PeopleRecord record) {
                records = new ArrayList<>();
                records.add(record);
                left = right = null;
            }
        }

        private BSTNode root;
        private Comparator<PeopleRecord> comparator;

        // 默认比较器：按 given name + family name 字典序（忽略大小写）
        public MyBST() {
            this((p1, p2) -> {
                int cmp = p1.getGivenName().compareToIgnoreCase(p2.getGivenName());
                if (cmp == 0) {
                    cmp = p1.getFamilyName().compareToIgnoreCase(p2.getFamilyName());
                }
                return cmp;
            });
        }

        public MyBST(Comparator<PeopleRecord> comparator) {
            this.root = null;
            this.comparator = comparator;
        }

        // 插入记录
        public void insert(PeopleRecord record) {
            root = insertRec(root, record);
        }

        private BSTNode insertRec(BSTNode node, PeopleRecord record) {
            if (node == null) {
                return new BSTNode(record);
            }
            int cmp = comparator.compare(record, node.records.get(0));
            if (cmp == 0) {
                node.records.add(record);          // 同名加入当前节点列表
            } else if (cmp < 0) {
                node.left = insertRec(node.left, record);
            } else {
                node.right = insertRec(node.right, record);
            }
            return node;
        }

        // 根据 givenName 和 familyName 搜索所有匹配记录
        public List<PeopleRecord> search(String givenName, String familyName) {
            PeopleRecord dummy = new PeopleRecord(givenName, familyName, "", "", "", "", "", "", "", "", "", "", "");
            return searchRec(root, dummy);
        }

        private List<PeopleRecord> searchRec(BSTNode node, PeopleRecord target) {
            if (node == null) {
                return new ArrayList<>();
            }
            int cmp = comparator.compare(target, node.records.get(0));
            if (cmp == 0) {
                return new ArrayList<>(node.records);   // 返回副本
            } else if (cmp < 0) {
                return searchRec(node.left, target);
            } else {
                return searchRec(node.right, target);
            }
        }

        // 返回 [节点数, 高度]
        public int[] getInfo() {
            int[] info = new int[2];
            info[0] = countNodes(root);
            info[1] = height(root);
            return info;
        }

        private int countNodes(BSTNode node) {
            if (node == null) return 0;
            return 1 + countNodes(node.left) + countNodes(node.right);
        }

        private int height(BSTNode node) {
            if (node == null) return -1;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        // 获取树中所有记录（用于堆排序）
        public List<PeopleRecord> getAllRecords() {
            List<PeopleRecord> all = new ArrayList<>();
            inorderCollect(root, all);
            return all;
        }

        private void inorderCollect(BSTNode node, List<PeopleRecord> list) {
            if (node == null) return;
            inorderCollect(node.left, list);
            list.addAll(node.records);
            inorderCollect(node.right, list);
        }
    }
}
