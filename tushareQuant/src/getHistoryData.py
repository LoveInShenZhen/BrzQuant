#!/usr/bin/env python3
# -*- coding:utf-8 -*-

import argparse
import tushare as ts
import json, os

parser = argparse.ArgumentParser(
    description='获取个股历史交易数据（包括均线数据）,可以通过参数设置获取日k线,周k线,月k线,以及5分钟,15分钟,30分钟和60分钟k线数据。本接口只能获取近3年的日线数据')

"""
ArgumentParser.add_argument(name or flags...[, action]
                                            [, nargs]
                                            [, const]
                                            [, default]
                                            [, type]
                                            [, choices]
                                            [, required]
                                            [, help]
                                            [, metavar]
                                            [, dest])
Define how a single command-line argument should be parsed.
Each parameter has its own more detailed description below, but in short they are:

    name or flags - Either a name or a list of option strings, e.g. foo or -f, --foo.
    action - The basic type of action to be taken when this argument is encountered at the command line.
    nargs - The number of command-line arguments that should be consumed.
    const - A constant value required by some action and nargs selections.
    default - The value produced if the argument is absent from the command line.
    type - The type to which the command-line argument should be converted.
    choices - A container of the allowable values for the argument.
    required - Whether or not the command-line option may be omitted (optionals only).
    help - A brief description of what the argument does.
    metavar - A name for the argument in usage messages.
    dest - The name of the attribute to be added to the object returned by parse_args().

"""

parser.add_argument('--code', help='股票代码，即6位数字代码，或者指数代码（sh=上证指数 sz=深圳成指 hs300=沪深300指数 sz50=上证50 zxb=中小板 cyb=创业板）',
                    type=str)
parser.add_argument('--start', help='开始日期，格式YYYY-MM-DD', type=str)
parser.add_argument('--end', help='结束日期，格式YYYY-MM-DD', type=str)
parser.add_argument('--ktype', help='数据类型，D=日k线 W=周 M=月 5=5分钟 15=15分钟 30=30分钟 60=60分钟，默认为D', type=str,
                    choices=['D', 'W', 'M', '15', '30', '60'], default='D')
parser.add_argument('--retry_count', help='当网络异常后重试次数，默认为3', type=int, default=3)
parser.add_argument('--pause', help='重试时停顿秒数,默认为0', type=int, default=0)
parser.add_argument('-d', '--dir', help='保存 csv 数据的目录', default="/Users/kk/ssdwork/github/tuShareData", type=str)
args = parser.parse_args()


def main():
    fname = '%s.%s.%s.csv' % (args.ktype, args.start, args.end)
    output_dir = os.path.join(args.dir, "get_hist_data", args.code)
    os.makedirs(output_dir, exist_ok=True)
    csvname = os.path.join(output_dir, fname)

    df = ts.get_hist_data(code=args.code, start=args.start, end=args.end, ktype=args.ktype,
                          retry_count=args.retry_count, pause=args.pause)

    df.to_csv(path_or_buf=csvname, float_format='%.6f')

    res = {'ret': 0, 'msg': 'OK', 'csvpath': csvname}
    if (df.empty):
        res['ret'] = -1
        res['msg'] = 'No Data'

    print(json.dumps(res, sort_keys=True, indent=4))
    exit(0)


if __name__ == '__main__':
    main()
