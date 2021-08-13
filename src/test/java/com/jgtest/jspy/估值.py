if __name__ == '__main__':
    # req('http://fund.eastmoney.com/Company/home/KFSFundNet?gsid=80000229&fundType=002')

    买入费率 = 0.0015

    持仓成本价 = 2.4615
    持有份额 = 3102.13

    待确认金额 = 1100
    基金净值 = 2.2701

    投入金额 = 持仓成本价 * 持有份额
    print('投入金额：' + str(投入金额))
    持有收益 = (基金净值 - 持仓成本价) * 持有份额
    print('持有收益：' + str(持有收益))
    持有金额 = 持仓成本价 * 持有份额 + 持有收益
    print('持有金额：' + str(持有金额))
    持仓收益率 = format(持有收益 / 投入金额, '.2%')
    print('持仓收益率：' + str(持仓收益率))

    增加份额 = 待确认金额 / 基金净值
    持有份额up = 持有份额 + 增加份额
    print('买入确认后 持有份额：   增加：' + str(增加份额) + ' || ' + str(持有份额) + ' ======> ' + str(持有份额up))
    增加收益 = - 待确认金额 * 买入费率
    持有收益up = 持有收益 - 待确认金额 * 买入费率
    print('买入确认后 持有收益：   增加：' + str(增加收益) + ' || ' + str(持有收益) + ' ======> ' + str(持有收益up))
    持仓成本价up = (持有金额 - 持有收益up + 待确认金额) / (持有份额up)
    增加持仓 = 持仓成本价up - 持仓成本价
    print('买入确认后 持仓成本价： 增加：' + str(增加持仓) + ' || ' + str(持仓成本价) + ' ======> ' + str(持仓成本价up))

    # 预期净值 = 2.2701
    # 预期收益 = (预期净值 - 基金净值) * 持有份额
    # print('买入确认后 当日预期收益' + str(预期收益))
