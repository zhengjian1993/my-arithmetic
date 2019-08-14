package com.cook.arithmetic.expand;

import java.util.Arrays;
import java.util.Random;

/**
 * BP神经网络
 * 神经元较多情况下，训练精度很差，时间也很长
 * @author zhengjian
 * @date 2018 -05 -16 15:36
 */
public class BpDeep {
    /**
     * 神经网络各层节点
     */
    private double[][] layer;
    /**
     * 神经网络各节点误差
     */
    private double[][] layerErr;
    /**
     * 各层节点权重
     */
    private double[][][] layerWeight;
    /**
     * 各层节点权重动量
     */
    private double[][][] layerWeightDelta;
    /**
     * 动量系数
     */
    private double moBp;
    /**
     * 学习系数
     */
    private double rate;

    /**
     * 初始化神经网络的基本配置
     * @param layerNum 整型数组，表示神经网络的层数和每层节点数，比如{3,10,10,10,10,2}表示输入层是3个节点，输出层是2个节点，中间有4层隐含层，每层10个节点
     * @param rate 学习步长
     * @param moBp 动量系数也叫学习率
     */
    private BpDeep(int[] layerNum, double rate, double moBp){
        this.moBp = moBp;
        this.rate = rate;
        layer = new double[layerNum.length][];
        layerErr = new double[layerNum.length][];
        layerWeight = new double[layerNum.length][][];
        layerWeightDelta = new double[layerNum.length][][];
        Random random = new Random();

        for(int l = 0; l < layerNum.length; l++){
            layer[l] = new double[layerNum[l]];
            layerErr[l] = new double[layerNum[l]];
            if(l + 1 < layerNum.length){
                layerWeight[l] = new double[layerNum[l]+1][layerNum[l+1]];
                layerWeightDelta[l] = new double[layerNum[l]+1][layerNum[l+1]];
                for(int j = 0; j < layerNum[l] + 1; j++) {
                    for (int i = 0; i < layerNum[l + 1]; i++) {
                        //随机初始化权重
                        layerWeight[l][j][i] = random.nextDouble();
                    }
                }
            }
        }
    }

    /**
     * 逐层向前计算输出
     */
    private double[] computeOut(double[] in){
        for(int l = 1; l < layer.length; l++){
            for(int j = 0; j < layer[l].length; j++){
                double z = layerWeight[l-1][layer[l-1].length][j];
                for(int i = 0; i < layer[l-1].length; i++){
                    layer[l-1][i] = (l == 1 ? in[i] : layer[l-1][i]);
                    z += layerWeight[l-1][i][j] * layer[l-1][i];
                }
                layer[l][j] = 1/(1 + Math.exp(-z));
            }
        }
        return layer[layer.length-1];
    }


    /**
     * 逐层反向计算误差并修改权重
     */
    private void updateWeight(double[] tar){
        int l = layer.length - 1;
        for(int j = 0; j < layerErr[l].length; j++) {
            layerErr[l][j] = layer[l][j] * (1 - layer[l][j]) * (tar[j] - layer[l][j]);
        }
        while(l-- > 0){
            for(int j = 0; j < layerErr[l].length; j++){
                double z = 0.0;
                for(int i = 0;i < layerErr[l+1].length; i++){
                    z = (z + l) > 0 ? layerErr[l+1][i] * layerWeight[l][j][i] : 0;
                    //隐含层动量调整
                    layerWeightDelta[l][j][i] = moBp * layerWeightDelta[l][j][i] + rate * layerErr[l+1][i] * layer[l][j];
                    //隐含层权重调整
                    layerWeight[l][j][i] += layerWeightDelta[l][j][i];
                    if(j == layerErr[l].length - 1){
                        //截距动量调整
                        layerWeightDelta[l][j+1][i] = moBp * layerWeightDelta[l][j+1][i] + rate * layerErr[l+1][i];
                        //截距权重调整
                        layerWeight[l][j+1][i] += layerWeightDelta[l][j+1][i];
                    }
                }
                //记录误差
                layerErr[l][j] = z * layer[l][j] * (1 - layer[l][j]);
            }
        }
    }

    private void train(double[] in, double[] tar){
        double[] out = computeOut(in);
        printVariance(out, tar, false);
        updateWeight(tar);
    }

    private static final int TRAIN_NUM = 50000;

    private void itTrain(double[][] data, double[][] target){
        //迭代训练5000次
        for(int n = 0; n < TRAIN_NUM; n++) {
            for (int i = 0; i < data.length; i++) {
                train(data[i], target[i]);
            }
        }
    }

    private void printVariance(double[] out, double[] tar, boolean isPrint) {
        double o = 0.0;
        for (int i = 0; i < out.length; i++) {
            o += Math.pow((out[i] - tar[i]), 2);
        }
        if (isPrint){
            System.out.println("该次训练的方差为：" + o);
        }
    }


    public static void main(String[] args){
        BpDeep bp = new BpDeep(new int[]{2,10,2}, 0.8, 0.5);
        Random random = new Random();
        int n = 100;
        double[][] data = new double[n][2];
        double[][] target = new double[n][2];
        for (int i = 0; i < n; i++){
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            data[i][0] = x;
            data[i][1] = y;
            if (x <= n/2 && y <= n/2){
                target[i] = new double[]{0, 0};
            }
            if (x > n/2 && y > n/2){
                target[i] = new double[]{1, 1};
            }
            if (x <= n/2 && y > n/2){
                target[i] = new double[]{0, 1};
            }
            if (x > n/2 && y <= n/2){
                target[i] = new double[]{1, 0};
            }
        }

        bp.itTrain(data, target);

//        //根据训练结果来检验样本数据
//        for(double[] j : data){
//            double[] result = bp.computeOut(j);
//            System.out.println(Arrays.toString(j)+":"+Arrays.toString(result));
//        }

        //根据训练结果来预测一条新数据的分类
        double[] x = new double[]{3,1};
        double[] result = bp.computeOut(x);
        System.out.println(Arrays.toString(x)+":"+Arrays.toString(result));
    }
}
