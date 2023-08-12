package project.seatsence.global.utils;

import java.util.ArrayList;
import java.util.List;

public class Sort<T extends Comparable<T>> {

    public void mergeSort(List<T> list) {
        sort(list, 0, list.size());
    }

    private void sort(List<T> list, int low, int high) {
        if (high - low < 2) {
            return;
        }

        int mid = (low + high) / 2;
        sort(list, 0, mid);
        sort(list, mid, high);
        merge(list, low, mid, high);
    }

    private void merge(List<T> list, int low, int mid, int high) {
        List<T> temp = new ArrayList<>();
        int t = 0, l = low, m = mid;

        while (l < mid && m < high) {
            if (list.get(l) < list.get(m)) {
                temp[t++] = list[l++];
            } else {
                temp[t++] = list[m++];
            }
        }

        while (l < mid) {
            temp[t++] = list[l++];
        }

        while (m < high) {
            temp[t++] = list[m++];
        }

        for (int i = low; i < high; i++) {
            list[i] = temp[i - low];
        }
    }
}
