#!/usr/bin/env python3
# -*- coding:utf-8 -*-

import argparse
import tushare as ts
import json, os

parser = argparse.ArgumentParser(
    description='获取指数历史数据（包括均线数据）,可以通过参数设置获取日k线,周k线,月k线,以及5分钟,15分钟,30分钟和60分钟k线数据。本接口只能获取近3年的日线数据')

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

parser.add_argument('--code', help='股票代码,即6位数字代码,或者指数代码',
                    type=str)
parser.add_argument('--start', help='开始日期,格式YYYY-MM-DD', type=str)
parser.add_argument('--end', help='结束日期,格式YYYY-MM-DD', type=str)
parser.add_argument('--autype', help='qfq-前复权 hfq-后复权 None-不复权,默认为qfq', type=str,
                    choices=['qfq', 'hfq', 'None'], default='qfq')
parser.add_argument('--index', help='是否是大盘指数,默认为False', action='store_true', default=False,)
parser.add_argument('--retry_count', help='当网络异常后重试次数,默认为3', type=int, default=3)
parser.add_argument('--pause', help='重试时停顿秒数,默认为0', type=float, default=0)
parser.add_argument('-d', '--dir', help='保存 csv 数据的目录', default="/Users/kk/ssdwork/github/tuShareData", type=str)
parser.add_argument('--fake', help='不获取数据,仅仅返回数据文件路径', action='store_true', default=False)
args = parser.parse_args()


def folder_name(args):
    index_map = {
        '000001': u'上证指数',
        '399001': u'深圳成指',
        '000300': u'沪深300指数',
        '000016': u'上证50指数',
        '399005': u'中小板指数',
        '399006': u'创业板指数',
        '399106': u'深证综合指数'
    }

    if args.index:
        return index_map[args.code]
    else:
        return args.code


def file_name(args):
    fq_map = {
        'qfq': '前复权',
        'hfq': '后复权',
        'None': '不复权'
    }

    if args.index:
        # 指数没有复权
        return '%s.%s.csv' % (args.start, args.end)
    else:
        return '%s.%s.%s.csv' % (fq_map[args.autype], args.start, args.end)


def main():
    res = {}
    try:
        fname = file_name(args)
        output_dir = os.path.join(args.dir, "get_h_data", folder_name(args))
        os.makedirs(output_dir, exist_ok=True)
        csvname = os.path.join(output_dir, fname)

        res['ret'] = 0
        res['msg'] = 'OK'
        res['csvpath'] = csvname

        if args.fake:
            print(json.dumps(res, sort_keys=True, indent=4, ensure_ascii=False))
            exit(0)
        else:
            df = ts.get_h_data(code=args.code,
                               start=args.start,
                               end=args.end,
                               autype=args.autype,
                               index=args.index,
                               retry_count=args.retry_count,
                               pause=args.pause)

            df.to_csv(path_or_buf=csvname, float_format='%.6f')

            if df.empty:
                res['ret'] = 99
                res['msg'] = 'No Data'

    except Exception as ex:
        res['ret'] = -1
        res['msg'] = str(ex)
        raise ex

    print(json.dumps(res, sort_keys=True, indent=4, ensure_ascii=False))
    exit(0)


if __name__ == '__main__':
    main()
